package com.endava.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {
    private String createdAt;
    private String updatedAt;
    private String slug;
    private String synopsis;
    private String canonicalTitle;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public Attributes() {

    }

    @Override
    public String toString() {
        return "Attributes{" +
                "createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", slug='" + slug + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", canonicalTitle='" + canonicalTitle + '\'' +
                '}';
    }
}
