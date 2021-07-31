package com.ngrok.services;

import com.ngrok.NgrokApiClient;
import com.ngrok.definitions.*;

import java.util.AbstractMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Tunnel Credentials are ngrok agent authtokens. They authorize the ngrok
 *  agent to connect the ngrok service as your account. They are installed with
 *  the <code>ngrok authtoken</code> command or by specifying it in the
 * <code>ngrok.yml</code>
 *  configuration file with the <code>authtoken</code> property.
 */
public class Credentials {
    private final NgrokApiClient apiClient;

    /**
     * Creates a new sub-client for Credentials.
     *
     * @param apiClient an instance of {@link com.ngrok.NgrokApiClient}
     */
    public Credentials(final NgrokApiClient apiClient) {
        this.apiClient = Objects.requireNonNull(apiClient, "apiClient is required");
    }
    
    /**
     * A builder object encapsulating state for an unsent Create API call.
     */
    public class CredentialsCreateCallBuilder {
        private String description = "";
        private String metadata = "";
        private java.util.List<String> acl = null;

        private CredentialsCreateCallBuilder(
        ) {
        }
        
        /**
         * human-readable description of who or what will use the credential to
         * authenticate. Optional, max 255 bytes.
         *
         * @param description the value of the description parameter as a {@link String}
         * @return the call builder instance
         */
        public CredentialsCreateCallBuilder description(final String description) {
            this.description = Objects.requireNonNull(description, "description is required");
            return this;
        }

        /**
         * human-readable description of who or what will use the credential to
         * authenticate. Optional, max 255 bytes.
         *
         * @param description the value of the description parameter as an {@link Optional} of {@link String}
         * @return the call builder instance
         */
        public CredentialsCreateCallBuilder description(final Optional<String> description) {
            this.description = Objects.requireNonNull(description, "description is required").orElse("");
            return this;
        }
        
        /**
         * arbitrary user-defined machine-readable data of this credential. Optional, max
         * 4096 bytes.
         *
         * @param metadata the value of the metadata parameter as a {@link String}
         * @return the call builder instance
         */
        public CredentialsCreateCallBuilder metadata(final String metadata) {
            this.metadata = Objects.requireNonNull(metadata, "metadata is required");
            return this;
        }

        /**
         * arbitrary user-defined machine-readable data of this credential. Optional, max
         * 4096 bytes.
         *
         * @param metadata the value of the metadata parameter as an {@link Optional} of {@link String}
         * @return the call builder instance
         */
        public CredentialsCreateCallBuilder metadata(final Optional<String> metadata) {
            this.metadata = Objects.requireNonNull(metadata, "metadata is required").orElse("");
            return this;
        }
        
        /**
         * optional list of ACL rules. If unspecified, the credential will have no
         * restrictions. The only allowed ACL rule at this time is the <code>bind</code>
         * rule. The <code>bind</code> rule allows the caller to restrict what domains and
         * addresses the token is allowed to bind. For example, to allow the token to open
         * a tunnel on example.ngrok.io your ACL would include the rule
         * <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to
         * match multiple domains with a common suffix. For example, you may specify a rule
         * of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>,
         * <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of
         * <code>'*'</code> is equivalent to no acl at all and will explicitly permit all
         * actions.
         *
         * @param acl the value of the acl parameter as a {@link java.util.List<String>}
         * @return the call builder instance
         */
        public CredentialsCreateCallBuilder acl(final java.util.List<String> acl) {
            this.acl = Objects.requireNonNull(acl, "acl is required");
            return this;
        }

        /**
         * optional list of ACL rules. If unspecified, the credential will have no
         * restrictions. The only allowed ACL rule at this time is the <code>bind</code>
         * rule. The <code>bind</code> rule allows the caller to restrict what domains and
         * addresses the token is allowed to bind. For example, to allow the token to open
         * a tunnel on example.ngrok.io your ACL would include the rule
         * <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to
         * match multiple domains with a common suffix. For example, you may specify a rule
         * of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>,
         * <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of
         * <code>'*'</code> is equivalent to no acl at all and will explicitly permit all
         * actions.
         *
         * @param acl the value of the acl parameter as an {@link Optional} of {@link java.util.List<String>}
         * @return the call builder instance
         */
        public CredentialsCreateCallBuilder acl(final Optional<java.util.List<String>> acl) {
            this.acl = Objects.requireNonNull(acl, "acl is required").orElse(null);
            return this;
        }
        
        /**
         * Initiates the API call asynchronously.
         *
         * @return a {@link CompletionStage} of {@link Credential}
         */
        public CompletionStage<Credential> call() {
            return apiClient.sendRequest(
                NgrokApiClient.HttpMethod.POST,
                "/credentials",
                Stream.empty(),
                Stream.of(
                    new AbstractMap.SimpleEntry<>("description", Optional.of(this.description)),
                    new AbstractMap.SimpleEntry<>("metadata", Optional.of(this.metadata)),
                    new AbstractMap.SimpleEntry<>("acl", Optional.of(this.acl))
                ),
                Optional.of(Credential.class)
            );
        }

        /**
         * Initiates the API call and blocks until it returns.
         *
         * @return {@link Credential}
         * @throws InterruptedException if the thread was interrupted during the call
         */
        public Credential blockingCall() throws InterruptedException {
            try {
                return call().toCompletableFuture().get();
            } catch (final ExecutionException e) {
                throw e.getCause() instanceof RuntimeException ? (RuntimeException) e.getCause() : new RuntimeException(e.getCause().getMessage(), e.getCause());
            }
        }
    }

    /**
     * Create a new tunnel authtoken credential. This authtoken credential can be used
     * to start a new tunnel session. The response to this API call is the only time
     * the generated token is available. If you need it for future use, you must save
     * it securely yourself.
     *
     * @return a call builder for this API call
     */
    public CredentialsCreateCallBuilder create(
    ) {
        return new CredentialsCreateCallBuilder(
        );
    }
    
    /**
     * A builder object encapsulating state for an unsent Delete API call.
     */
    public class CredentialsDeleteCallBuilder {
        private final String id;

        private CredentialsDeleteCallBuilder(
            final String id
        ) {
            this.id = Objects.requireNonNull(id, "id is required");
        }
        
        /**
         * Initiates the API call asynchronously.
         *
         * @return a {@link CompletionStage} of {@link Void}
         */
        public CompletionStage<Void> call() {
            return apiClient.sendRequest(
                NgrokApiClient.HttpMethod.DELETE,
                "/credentials/" + this.id,
                Stream.empty(),
                Stream.empty(),
                Optional.empty()
            );
        }

        /**
         * Initiates the API call and blocks until it returns.
         *
         * @throws InterruptedException if the thread was interrupted during the call
         */
        public void blockingCall() throws InterruptedException {
            try {
                call().toCompletableFuture().get();
            } catch (final ExecutionException e) {
                throw e.getCause() instanceof RuntimeException ? (RuntimeException) e.getCause() : new RuntimeException(e.getCause().getMessage(), e.getCause());
            }
        }
    }

    /**
     * Delete a tunnel authtoken credential by ID
     *
     * @param id a resource identifier
     * @return a call builder for this API call
     */
    public CredentialsDeleteCallBuilder delete(
        final String id
    ) {
        return new CredentialsDeleteCallBuilder(
            id
        );
    }
    
    /**
     * A builder object encapsulating state for an unsent Get API call.
     */
    public class CredentialsGetCallBuilder {
        private final String id;

        private CredentialsGetCallBuilder(
            final String id
        ) {
            this.id = Objects.requireNonNull(id, "id is required");
        }
        
        /**
         * Initiates the API call asynchronously.
         *
         * @return a {@link CompletionStage} of {@link Credential}
         */
        public CompletionStage<Credential> call() {
            return apiClient.sendRequest(
                NgrokApiClient.HttpMethod.GET,
                "/credentials/" + this.id,
                Stream.empty(),
                Stream.empty(),
                Optional.of(Credential.class)
            );
        }

        /**
         * Initiates the API call and blocks until it returns.
         *
         * @return {@link Credential}
         * @throws InterruptedException if the thread was interrupted during the call
         */
        public Credential blockingCall() throws InterruptedException {
            try {
                return call().toCompletableFuture().get();
            } catch (final ExecutionException e) {
                throw e.getCause() instanceof RuntimeException ? (RuntimeException) e.getCause() : new RuntimeException(e.getCause().getMessage(), e.getCause());
            }
        }
    }

    /**
     * Get detailed information about a tunnel authtoken credential
     *
     * @param id a resource identifier
     * @return a call builder for this API call
     */
    public CredentialsGetCallBuilder get(
        final String id
    ) {
        return new CredentialsGetCallBuilder(
            id
        );
    }
    
    /**
     * A builder object encapsulating state for an unsent List API call.
     */
    public class CredentialsListCallBuilder {
        private Optional<String> beforeId = Optional.empty();
        private Optional<String> limit = Optional.empty();

        private CredentialsListCallBuilder(
        ) {
        }
        
        /**
         * Sets the <code>before_id</code> parameter.
         *
         * @param beforeId the value of the before_id parameter as a {@link String}
         * @return the call builder instance
         */
        public CredentialsListCallBuilder beforeId(final String beforeId) {
            this.beforeId = Optional.ofNullable(beforeId);
            return this;
        }

        /**
         * Sets (or unsets) the <code>before_id</code> parameter.
         *
         * @param beforeId the value of the before_id parameter as an {@link Optional} of {@link String}
         * @return the call builder instance
         */
        public CredentialsListCallBuilder beforeId(final Optional<String> beforeId) {
            this.beforeId = Objects.requireNonNull(beforeId, "beforeId is required");
            return this;
        }
        
        /**
         * Sets the <code>limit</code> parameter.
         *
         * @param limit the value of the limit parameter as a {@link String}
         * @return the call builder instance
         */
        public CredentialsListCallBuilder limit(final String limit) {
            this.limit = Optional.ofNullable(limit);
            return this;
        }

        /**
         * Sets (or unsets) the <code>limit</code> parameter.
         *
         * @param limit the value of the limit parameter as an {@link Optional} of {@link String}
         * @return the call builder instance
         */
        public CredentialsListCallBuilder limit(final Optional<String> limit) {
            this.limit = Objects.requireNonNull(limit, "limit is required");
            return this;
        }
        
        /**
         * Initiates the API call asynchronously.
         *
         * @return a {@link CompletionStage} of a {@link Page} of {@link CredentialList}
         */
        public CompletionStage<Page<CredentialList>> call() {
            return apiClient.sendRequest(
                NgrokApiClient.HttpMethod.GET,
                "/credentials",
                Stream.of(
                    new AbstractMap.SimpleEntry<>("before_id", this.beforeId.map(Function.identity())),
                    new AbstractMap.SimpleEntry<>("limit", this.limit.map(Function.identity()))
                ),
                Stream.empty(),
                Optional.of(CredentialList.class)
            ).thenApply(list -> new Page<>(apiClient, list));
        }

        /**
         * Initiates the API call and blocks until it returns.
         *
         * @return a {@link Page} of {@link CredentialList}
         * @throws InterruptedException if the thread was interrupted during the call
         */
        public Page<CredentialList> blockingCall() throws InterruptedException {
            try {
                return call().toCompletableFuture().get();
            } catch (final ExecutionException e) {
                throw e.getCause() instanceof RuntimeException ? (RuntimeException) e.getCause() : new RuntimeException(e.getCause().getMessage(), e.getCause());
            }
        }
    }

    /**
     * List all tunnel authtoken credentials on this account
     *
     * @return a call builder for this API call
     */
    public CredentialsListCallBuilder list(
    ) {
        return new CredentialsListCallBuilder(
        );
    }
    
    /**
     * A builder object encapsulating state for an unsent Update API call.
     */
    public class CredentialsUpdateCallBuilder {
        private final String id;
        private Optional<String> description = Optional.empty();
        private Optional<String> metadata = Optional.empty();
        private Optional<java.util.List<String>> acl = Optional.empty();

        private CredentialsUpdateCallBuilder(
            final String id
        ) {
            this.id = Objects.requireNonNull(id, "id is required");
        }
        
        /**
         * human-readable description of who or what will use the credential to
         * authenticate. Optional, max 255 bytes.
         *
         * @param description the value of the description parameter as a {@link String}
         * @return the call builder instance
         */
        public CredentialsUpdateCallBuilder description(final String description) {
            this.description = Optional.ofNullable(description);
            return this;
        }

        /**
         * human-readable description of who or what will use the credential to
         * authenticate. Optional, max 255 bytes.
         *
         * @param description the value of the description parameter as an {@link Optional} of {@link String}
         * @return the call builder instance
         */
        public CredentialsUpdateCallBuilder description(final Optional<String> description) {
            this.description = Objects.requireNonNull(description, "description is required");
            return this;
        }
        
        /**
         * arbitrary user-defined machine-readable data of this credential. Optional, max
         * 4096 bytes.
         *
         * @param metadata the value of the metadata parameter as a {@link String}
         * @return the call builder instance
         */
        public CredentialsUpdateCallBuilder metadata(final String metadata) {
            this.metadata = Optional.ofNullable(metadata);
            return this;
        }

        /**
         * arbitrary user-defined machine-readable data of this credential. Optional, max
         * 4096 bytes.
         *
         * @param metadata the value of the metadata parameter as an {@link Optional} of {@link String}
         * @return the call builder instance
         */
        public CredentialsUpdateCallBuilder metadata(final Optional<String> metadata) {
            this.metadata = Objects.requireNonNull(metadata, "metadata is required");
            return this;
        }
        
        /**
         * optional list of ACL rules. If unspecified, the credential will have no
         * restrictions. The only allowed ACL rule at this time is the <code>bind</code>
         * rule. The <code>bind</code> rule allows the caller to restrict what domains and
         * addresses the token is allowed to bind. For example, to allow the token to open
         * a tunnel on example.ngrok.io your ACL would include the rule
         * <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to
         * match multiple domains with a common suffix. For example, you may specify a rule
         * of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>,
         * <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of
         * <code>'*'</code> is equivalent to no acl at all and will explicitly permit all
         * actions.
         *
         * @param acl the value of the acl parameter as a {@link java.util.List<String>}
         * @return the call builder instance
         */
        public CredentialsUpdateCallBuilder acl(final java.util.List<String> acl) {
            this.acl = Optional.ofNullable(acl);
            return this;
        }

        /**
         * optional list of ACL rules. If unspecified, the credential will have no
         * restrictions. The only allowed ACL rule at this time is the <code>bind</code>
         * rule. The <code>bind</code> rule allows the caller to restrict what domains and
         * addresses the token is allowed to bind. For example, to allow the token to open
         * a tunnel on example.ngrok.io your ACL would include the rule
         * <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to
         * match multiple domains with a common suffix. For example, you may specify a rule
         * of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>,
         * <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of
         * <code>'*'</code> is equivalent to no acl at all and will explicitly permit all
         * actions.
         *
         * @param acl the value of the acl parameter as an {@link Optional} of {@link java.util.List<String>}
         * @return the call builder instance
         */
        public CredentialsUpdateCallBuilder acl(final Optional<java.util.List<String>> acl) {
            this.acl = Objects.requireNonNull(acl, "acl is required");
            return this;
        }
        
        /**
         * Initiates the API call asynchronously.
         *
         * @return a {@link CompletionStage} of {@link Credential}
         */
        public CompletionStage<Credential> call() {
            return apiClient.sendRequest(
                NgrokApiClient.HttpMethod.PATCH,
                "/credentials/" + this.id,
                Stream.empty(),
                Stream.of(
                    new AbstractMap.SimpleEntry<>("description", this.description.map(Function.identity())),
                    new AbstractMap.SimpleEntry<>("metadata", this.metadata.map(Function.identity())),
                    new AbstractMap.SimpleEntry<>("acl", this.acl.map(Function.identity()))
                ),
                Optional.of(Credential.class)
            );
        }

        /**
         * Initiates the API call and blocks until it returns.
         *
         * @return {@link Credential}
         * @throws InterruptedException if the thread was interrupted during the call
         */
        public Credential blockingCall() throws InterruptedException {
            try {
                return call().toCompletableFuture().get();
            } catch (final ExecutionException e) {
                throw e.getCause() instanceof RuntimeException ? (RuntimeException) e.getCause() : new RuntimeException(e.getCause().getMessage(), e.getCause());
            }
        }
    }

    /**
     * Update attributes of an tunnel authtoken credential by ID
     *
     * @param id the value of the <code>id</code> parameter as a {@link String}
     * @return a call builder for this API call
     */
    public CredentialsUpdateCallBuilder update(
        final String id
    ) {
        return new CredentialsUpdateCallBuilder(
            id
        );
    }
}
