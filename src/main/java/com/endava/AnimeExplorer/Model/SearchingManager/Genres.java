package com.endava.AnimeExplorer.Model.SearchingManager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Genres {

     private  GenresAttribute attributes;

    public GenresAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(GenresAttribute attributes) {
        this.attributes = attributes;
    }

    public Genres() {

    }



}
