package com.endava.example;

import java.util.Arrays;

public class Page {

    private SimpleAnime[] data;
    private Meta meta;
    private Links links;


    public SimpleAnime[] getData() {
        return data;
    }

    public void setData(SimpleAnime[] data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Page{" +
                "data=" + Arrays.toString(data) +
                ", meta=" + meta +
                ", links=" + links +
                '}';
    }
}
