package com.ngrok.definitions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Optional;

/**
 * A class encapsulating the {@link Credential} resource.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credential {
    @JsonProperty("id")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final String id;
    @JsonProperty("uri")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final java.net.URI uri;
    @JsonProperty("created_at")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final java.time.OffsetDateTime createdAt;
    @JsonProperty("description")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final String description;
    @JsonProperty("metadata")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final String metadata;
    @JsonProperty("token")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final Optional<String> token;
    @JsonProperty("acl")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final java.util.List<String> acl;

    /**
     * Creates a new instance of {@link Credential}.
     *
     * @param id unique tunnel credential resource identifier
     * @param uri URI of the tunnel credential API resource
     * @param createdAt timestamp when the tunnel credential was created, RFC 3339 format
     * @param description human-readable description of who or what will use the credential to authenticate. Optional, max 255 bytes.
     * @param metadata arbitrary user-defined machine-readable data of this credential. Optional, max 4096 bytes.
     * @param token the credential's authtoken that can be used to authenticate an ngrok agent. <strong>This value is only available one time, on the API response from credential creation, otherwise it is null.</strong>
     * @param acl optional list of ACL rules. If unspecified, the credential will have no restrictions. The only allowed ACL rule at this time is the <code>bind</code> rule. The <code>bind</code> rule allows the caller to restrict what domains and addresses the token is allowed to bind. For example, to allow the token to open a tunnel on example.ngrok.io your ACL would include the rule <code>bind:example.ngrok.io</code>. Bind rules may specify a leading wildcard to match multiple domains with a common suffix. For example, you may specify a rule of <code>bind:*.example.com</code> which will allow <code>x.example.com</code>, <code>y.example.com</code>, <code>*.example.com</code>, etc. A rule of <code>'*'</code> is equivalent to no acl at all and will explicitly permit all actions.
     */
    @JsonCreator
    public Credential(
        @JsonProperty("id") final String id,
        @JsonProperty("uri") final java.net.URI uri,
        @JsonProperty("created_at") final java.time.OffsetDateTime createdAt,
        @JsonProperty("description") final String description,
        @JsonProperty("metadata") final String metadata,
        @JsonProperty("token") final Optional<String> token,
        @JsonProperty("acl") final java.util.List<String> acl
    ) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.uri = Objects.requireNonNull(uri, "uri is required");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
        this.description = Objects.requireNonNull(description, "description is required");
        this.metadata = Objects.requireNonNull(metadata, "metadata is required");
        this.token = token != null ? token : Optional.empty();
        this.acl = Objects.requireNonNull(acl, "acl is required");
    }

    /**
     * unique tunnel credential resource identifier
     *
     * @return the value of the property as a {@link String}
     */
    public String getId() {
        return this.id;
    }

    /**
     * URI of the tunnel credential API resource
     *
     * @return the value of the property as a {@link java.net.URI}
     */
    public java.net.URI getUri() {
        return this.uri;
    }

    /**
     * timestamp when the tunnel credential was created, RFC 3339 format
     *
     * @return the value of the property as a {@link java.time.OffsetDateTime}
     */
    public java.time.OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * human-readable description of who or what will use the credential to
     * authenticate. Optional, max 255 bytes.
     *
     * @return the value of the property as a {@link String}
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * arbitrary user-defined machine-readable data of this credential. Optional, max
     * 4096 bytes.
     *
     * @return the value of the property as a {@link String}
     */
    public String getMetadata() {
        return this.metadata;
    }

    /**
     * the credential's authtoken that can be used to authenticate an ngrok agent.
     * <strong>This value is only available one time, on the API response from
     * credential creation, otherwise it is null.</strong>
     *
     * @return the value of the property as a {@link String} wrapped in an {@link Optional}
     */
    public Optional<String> getToken() {
        return this.token;
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
     * @return the value of the property as a {@link java.util.List<String>}
     */
    public java.util.List<String> getAcl() {
        return this.acl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final Credential other = (Credential) o;
        return
            this.id.equals(other.id)&&
            this.uri.equals(other.uri)&&
            this.createdAt.equals(other.createdAt)&&
            this.description.equals(other.description)&&
            this.metadata.equals(other.metadata)&&
            this.token.equals(other.token)&&
            this.acl.equals(other.acl);
        
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.id,
            this.uri,
            this.createdAt,
            this.description,
            this.metadata,
            this.token,
            this.acl
        );
    }

    @Override
    public String toString() {
        return "Credential{" +
            "id='" + this.id +
            "', uri='" + this.uri +
            "', createdAt='" + this.createdAt +
            "', description='" + this.description +
            "', metadata='" + this.metadata +
            "', token='" + this.token.orElse("(null)") +
            "', acl='" + this.acl +
            "'}";
    }
}
