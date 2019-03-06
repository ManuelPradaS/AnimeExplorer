package com.endava.AnimeExplorer.Model.SearchingManager;

public class Page {

    private AnimeSingle[] data;
    private Meta meta;
    private Links links;


    public AnimeSingle[] getData() {
        return data;
    }

    public void setData(AnimeSingle[] data) {
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


}
