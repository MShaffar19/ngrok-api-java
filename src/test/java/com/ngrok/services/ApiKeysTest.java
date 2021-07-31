package com.ngrok.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linecorp.armeria.common.HttpHeaderNames;
import com.ngrok.ApiKeyTestBase;
import com.ngrok.DefaultNgrokApiClient;
import com.ngrok.Ngrok;
import com.ngrok.NgrokApiClient;
import com.ngrok.Version;
import com.ngrok.definitions.ApiKey;
import com.ngrok.definitions.ApiKeyList;
import com.ngrok.definitions.Page;
import de.mkammerer.wiremock.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiKeysTest extends ApiKeyTestBase {
    private static final String FAKE_API_SECRET ="s3kr1t";

    private static final Map<String, Object> API_KEY_CREATE = API_KEY_JSON_FIELDS.entrySet().stream()
        .filter(entry -> entry.getKey().equals("description") || entry.getKey().equals("metadata"))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static final Map<String, Object> API_KEY_UPDATE = Stream.of(
        new AbstractMap.SimpleEntry<>("description", "this is an UPDATED description")
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static final ApiKey OTHER_API_KEY = new ApiKey(
        "asdadsda",
        URI.create("https://api.ngrok.com/api_keys/asdasdsda"),
        "",
        "",
        OffsetDateTime.now(),
        Optional.of("12345")
    );

    private static final ApiKeyList API_KEY_LIST = new ApiKeyList(
        Arrays.asList(API_KEY_NO_TOKEN, OTHER_API_KEY),
        URI.create("https://api.ngrok.com/api_keys"),
        Optional.empty()
    );

    private static final ApiKey UPDATED_API_KEY = new ApiKey(
        (String) API_KEY_JSON_FIELDS.get("id"),
        (URI) API_KEY_JSON_FIELDS.get("uri"),
        (String) API_KEY_UPDATE.get("description"),
        (String) API_KEY_JSON_FIELDS.get("metadata"),
        (OffsetDateTime) API_KEY_JSON_FIELDS.get("created_at"),
        Optional.empty()
    );

    @RegisterExtension
    final WireMockExtension wireMock = new WireMockExtension();

    private Ngrok ngrok() {
        final NgrokApiClient apiClient = DefaultNgrokApiClient
            .newBuilder(USE_LIVE_API ? System.getenv("NGROK_API_KEY") : FAKE_API_SECRET)
            .baseUri(USE_LIVE_API ? NgrokApiClient.DEFAULT_BASE_URI : wireMock.getBaseUri())
            .build();
        return new Ngrok(apiClient);
    }

    @BeforeEach
    public void stubApiKeys() throws JsonProcessingException {
        if (USE_LIVE_API) {
            return;
        }

        wireMock.resetAll();

        wireMock.stubFor(
            post(urlPathEqualTo("/api_keys"))
                .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo("Bearer " + FAKE_API_SECRET))
                .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo("ngrok-api-client-java/" + Version.CLIENT_VERSION))
                .withHeader("ngrok-version", equalTo(Version.API_VERSION))
                .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), containing("application/json"))
                .withRequestBody(equalTo(MAPPER.writeValueAsString(API_KEY_CREATE)))
                .willReturn(ok(MAPPER.writeValueAsString(API_KEY))
                    .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
                )
        );

        wireMock.stubFor(
            get(urlPathEqualTo("/api_keys/" + API_KEY_JSON_FIELDS.get("id")))
                .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo("Bearer " + FAKE_API_SECRET))
                .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo("ngrok-api-client-java/" + Version.CLIENT_VERSION))
                .withHeader("ngrok-version", equalTo(Version.API_VERSION))
                .willReturn(ok(MAPPER.writeValueAsString(API_KEY_NO_TOKEN))
                    .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
                )
        );

        wireMock.stubFor(
            get(urlPathEqualTo("/api_keys"))
                .withQueryParam("limit", equalTo("10"))
                .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo("Bearer " + FAKE_API_SECRET))
                .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo("ngrok-api-client-java/" + Version.CLIENT_VERSION))
                .withHeader("ngrok-version", equalTo(Version.API_VERSION))
                .willReturn(ok(MAPPER.writeValueAsString(API_KEY_LIST))
                    .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
                )
        );

        wireMock.stubFor(
            patch(urlPathEqualTo("/api_keys/" + API_KEY_JSON_FIELDS.get("id")))
                .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo("Bearer " + FAKE_API_SECRET))
                .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo("ngrok-api-client-java/" + Version.CLIENT_VERSION))
                .withHeader("ngrok-version", equalTo(Version.API_VERSION))
                .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), containing("application/json"))
                .withRequestBody(equalTo(MAPPER.writeValueAsString(API_KEY_UPDATE)))
                .willReturn(ok(MAPPER.writeValueAsString(UPDATED_API_KEY))
                    .withHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "application/json")
                )
        );

        wireMock.stubFor(
            delete(urlPathEqualTo("/api_keys/" + API_KEY_JSON_FIELDS.get("id")))
                .withHeader(HttpHeaderNames.AUTHORIZATION.toString(), equalTo("Bearer " + FAKE_API_SECRET))
                .withHeader(HttpHeaderNames.USER_AGENT.toString(), equalTo("ngrok-api-client-java/" + Version.CLIENT_VERSION))
                .withHeader("ngrok-version", equalTo(Version.API_VERSION))
                .willReturn(noContent())
        );
    }

    @Test
    public void testApiKeys() throws ExecutionException, InterruptedException, TimeoutException {
        final String id = testCreateApiKey();
        try {
            testGetApiKey(id);
            testListApiKeys(id);
            testUpdateApiKey(id);
            testDeleteApiKey(id);
        } catch (final Exception e) {
            if (USE_LIVE_API) {
                // Make an attempt to clean things up
                ngrok().apiKeys().delete(id).call();
            }
            throw e;
        }
    }

    private String testCreateApiKey() throws ExecutionException, InterruptedException, TimeoutException {
        final ApiKeys apiKeys = ngrok().apiKeys();
        final ApiKey apiKey = apiKeys.create()
            .description((String) API_KEY_CREATE.get("description"))
            .metadata((String) API_KEY_CREATE.get("metadata"))
            .call().toCompletableFuture().get(5, TimeUnit.SECONDS);
        assertApiKeyFields(USE_LIVE_API, apiKey);
        return apiKey.getId();
    }

    private void testGetApiKey(final String id) throws ExecutionException, InterruptedException, TimeoutException {
        final ApiKeys apiKeys = ngrok().apiKeys();
        final ApiKey apiKey = apiKeys.get(id)
            .call().toCompletableFuture().get(5, TimeUnit.SECONDS);
        assertApiKeyFieldsNoToken(USE_LIVE_API, apiKey);
    }

    public void testListApiKeys(final String id) throws ExecutionException, InterruptedException, TimeoutException {
        final ApiKeys apiKeys = ngrok().apiKeys();
        final Page<ApiKeyList> apiKeyListPage = apiKeys.list()
            .limit("10")
            .call().toCompletableFuture().get(5, TimeUnit.SECONDS);
        if (USE_LIVE_API) {
            final ApiKey apiKey = apiKeyListPage.getPage().getKeys()
                .stream()
                .filter(key -> key.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("API key with ID " + id + " not found in list"));
            assertApiKeyFieldsNoToken(true, apiKey);
        } else {
            assertApiKeyFieldsNoToken(false, apiKeyListPage.getPage().getKeys().get(0));
            assertThat(apiKeyListPage.getPage().getKeys().get(1).getId()).isEqualTo("asdadsda");
            assertThat(apiKeyListPage.getPage().getKeys().get(1).getToken()).contains("12345");
            assertThat(apiKeyListPage.getPage().getNextPageUri()).isEmpty();
            assertThat(apiKeyListPage.next()).isCompletedWithValue(Optional.empty());
        }
    }

    private void testUpdateApiKey(final String id) throws ExecutionException, InterruptedException, TimeoutException {
        final ApiKeys apiKeys = ngrok().apiKeys();
        final ApiKey apiKey = apiKeys.update(id)
            .description((String) API_KEY_UPDATE.get("description"))
            .call().toCompletableFuture().get(5, TimeUnit.SECONDS);
        assertApiKeyFields(USE_LIVE_API, apiKey, (String) API_KEY_UPDATE.get("description"), false);
    }

    private void testDeleteApiKey(final String id) throws ExecutionException, InterruptedException, TimeoutException {
        final ApiKeys apiKeys = ngrok().apiKeys();
        apiKeys.delete(id).call().toCompletableFuture().get(5, TimeUnit.SECONDS);
    }
}