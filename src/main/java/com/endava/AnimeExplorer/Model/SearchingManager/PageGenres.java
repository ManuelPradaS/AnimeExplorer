package com.endava.AnimeExplorer.Model.SearchingManager;

public class PageGenres {

    private Genres[] data;

    private Meta meta;
    private Links links;

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

    public Genres[] getData() {
        return data;
    }

    public void setData(Genres[] data) {
        this.data = data;
    }

    public PageGenres() {
    }


}
