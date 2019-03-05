package com.endava.AnimeExplorer.Model.SearchingManager;

public class AttributesSingle {

    private String canonicalTitle;

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public AttributesSingle() {

    }


    @Override
    public String toString() {
        return "AttributesSingle{" +
                "canonicalTitle='" + canonicalTitle + '\'' +
                '}';
    }
}
