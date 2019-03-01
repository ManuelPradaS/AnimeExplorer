package com.endava.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleAnime {
    private int id;
    private SimpleLinks links;
    private SimpleAttributes attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SimpleLinks getLinks() {
        return links;
    }

    public void setLinks(SimpleLinks links) {
        this.links = links;
    }

    public SimpleAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(SimpleAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "SimpleAnime{" +
                "id=" + id +
                ", links=" + links +
                ", attributes=" + attributes +
                '}';
    }
}
