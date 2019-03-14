package com.endava.AnimeExplorer.model.searchManager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimeSingle {

    private int id;
    private LinksSingle links;
    private AttributesSingle attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinksSingle getLinks() {
        return links;
    }

    public void setLinks(LinksSingle links) {
        this.links = links;
    }

    public AttributesSingle getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesSingle attributes) {
        this.attributes = attributes;
    }


}
