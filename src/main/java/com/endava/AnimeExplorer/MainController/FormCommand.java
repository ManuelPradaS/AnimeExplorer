package com.endava.AnimeExplorer.MainController;


public class FormCommand {

        String[] genres;

        String[]  streamers;

        String[]  ageRatings;

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
