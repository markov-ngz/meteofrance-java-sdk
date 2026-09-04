package ngz.meteofrance.paquet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/** List of Grids availables. */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeteoFranceGridResponse {

    // Required fields
    private String title;
    private String description;
    private String attribution;

    // Optional fields (if any, though all seem required in your payload)
    private List<Link> links;

    // Getters and Setters
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("attribution")
    public String getAttribution() {
        return attribution;
    }

    @JsonProperty("attribution")
    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    /** Nested class for Link. */
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Link {
        private String href;
        private String rel;
        private String type;
        private String title;
        private Optional<String> referenceTime;
        private Optional<String> time;
        private Optional<String> insertTime;

        // Getters and Setters
        @JsonProperty("href")
        public String getHref() {
            return href;
        }

        @JsonProperty("href")
        public void setHref(String href) {
            this.href = href;
        }

        @JsonProperty("rel")
        public String getRel() {
            return rel;
        }

        @JsonProperty("rel")
        public void setRel(String rel) {
            this.rel = rel;
        }

        @JsonProperty("type")
        public String getType() {
            return type;
        }

        @JsonProperty("type")
        public void setType(String type) {
            this.type = type;
        }

        @JsonProperty("title")
        public String getTitle() {
            return title;
        }

        @JsonProperty("title")
        public void setTitle(String title) {
            this.title = title;
        }

        @JsonProperty("reference_time")
        public Optional<String> getReferenceTime() {
            return referenceTime;
        }

        @JsonProperty("reference_time")
        public void setReferenceTime(String referenceTime) {
            this.referenceTime = Optional.ofNullable(referenceTime);
        }

        @JsonProperty("time")
        public Optional<String> getTime() {
            return time;
        }

        @JsonProperty("time")
        public void setTime(String time) {
            this.time = Optional.ofNullable(time);
        }

        @JsonProperty("insert_time")
        public Optional<String> getInsertTime() {
            return insertTime;
        }

        @JsonProperty("insert_time")
        public void setInsertTime(String insertTime) {
            this.insertTime = Optional.ofNullable(insertTime);
        }
    }
}
