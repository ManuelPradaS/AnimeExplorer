package com.endava.AnimeExplorer.controllers;

public class FormCommand {

    String[] genres;
    String[] streamers;
    String[] ageRatings;
    String[] seasons;

    public String[] getSeasons() {
        return seasons;
    }

    public void setSeasons(String[] seasons) {
        this.seasons = seasons;
    }

    public String[] getAgeRatings() {
        return ageRatings;
    }

    public void setAgeRatings(String[] ageRatings) {
        this.ageRatings = ageRatings;
    }

    public String[] getStreamers() {
        return streamers;
    }

    public void setStreamers(String[] streamers) {
        this.streamers = streamers;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public FormCommand() {

    }

}
