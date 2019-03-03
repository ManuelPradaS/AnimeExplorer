package com.endava.AnimeExplorer.Model.SearchingManager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenresAttribute {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenresAttribute() {

    }

    @Override
    public String toString() {
        return
                 name + "____/" ;
    }
}
