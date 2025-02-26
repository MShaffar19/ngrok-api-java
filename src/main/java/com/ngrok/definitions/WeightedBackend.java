package com.ngrok.definitions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Optional;

/**
 * A class encapsulating the {@link WeightedBackend} resource.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeightedBackend {
    @JsonProperty("id")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final String id;
    @JsonProperty("uri")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final String uri;
    @JsonProperty("created_at")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final java.time.OffsetDateTime createdAt;
    @JsonProperty("description")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final String description;
    @JsonProperty("metadata")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final String metadata;
    @JsonProperty("backends")
    @JsonInclude(value = JsonInclude.Include.NON_ABSENT)
    private final java.util.Map<String, Long> backends;

    /**
     * Creates a new instance of {@link WeightedBackend}.
     *
     * @param id unique identifier for this Weighted backend
     * @param uri URI of the WeightedBackend API resource
     * @param createdAt timestamp when the backend was created, RFC 3339 format
     * @param description human-readable description of this backend. Optional
     * @param metadata arbitrary user-defined machine-readable data of this backend. Optional
     * @param backends the ids of the child backends to their weights [0-10000]
     */
    @JsonCreator
    public WeightedBackend(
        @JsonProperty("id") final String id,
        @JsonProperty("uri") final String uri,
        @JsonProperty("created_at") final java.time.OffsetDateTime createdAt,
        @JsonProperty("description") final String description,
        @JsonProperty("metadata") final String metadata,
        @JsonProperty("backends") final java.util.Map<String, Long> backends
    ) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.uri = Objects.requireNonNull(uri, "uri is required");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
        this.description = Objects.requireNonNull(description, "description is required");
        this.metadata = Objects.requireNonNull(metadata, "metadata is required");
        this.backends = Objects.requireNonNull(backends, "backends is required");
    }

    /**
     * unique identifier for this Weighted backend
     *
     * @return the value of the property as a {@link String}
     */
    public String getId() {
        return this.id;
    }

    /**
     * URI of the WeightedBackend API resource
     *
     * @return the value of the property as a {@link String}
     */
    public String getUri() {
        return this.uri;
    }

    /**
     * timestamp when the backend was created, RFC 3339 format
     *
     * @return the value of the property as a {@link java.time.OffsetDateTime}
     */
    public java.time.OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * human-readable description of this backend. Optional
     *
     * @return the value of the property as a {@link String}
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * arbitrary user-defined machine-readable data of this backend. Optional
     *
     * @return the value of the property as a {@link String}
     */
    public String getMetadata() {
        return this.metadata;
    }

    /**
     * the ids of the child backends to their weights [0-10000]
     *
     * @return the value of the property as a {@link java.util.Map<String, Long>}
     */
    public java.util.Map<String, Long> getBackends() {
        return this.backends;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final WeightedBackend other = (WeightedBackend) o;
        return
            this.id.equals(other.id)&&
            this.uri.equals(other.uri)&&
            this.createdAt.equals(other.createdAt)&&
            this.description.equals(other.description)&&
            this.metadata.equals(other.metadata)&&
            this.backends.equals(other.backends);
        
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.id,
            this.uri,
            this.createdAt,
            this.description,
            this.metadata,
            this.backends
        );
    }

    @Override
    public String toString() {
        return "WeightedBackend{" +
            "id='" + this.id +
            "', uri='" + this.uri +
            "', createdAt='" + this.createdAt +
            "', description='" + this.description +
            "', metadata='" + this.metadata +
            "', backends='" + this.backends +
            "'}";
    }
}
