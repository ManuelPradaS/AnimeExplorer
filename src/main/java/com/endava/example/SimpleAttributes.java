package com.endava.example;

public class SimpleAttributes {

    private String canonicalTitle;


    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public SimpleAttributes() {

    }


    @Override
    public String toString() {
        return "SimpleAttributes{" +
                "canonicalTitle='" + canonicalTitle + '\'' +
                '}';
    }
}
