package com.endava.AnimeExplorer.model.listManager;

public class MyLists {

    private String[] planToWatch;
    private String[] watched;
    private String[] favorites;

    public String[] getPlanToWatch() {
        return planToWatch;
    }

    public void setPlanToWatch(String[] planToWatch) {
        this.planToWatch = planToWatch;
    }

    public String[] getWatched() {
        return watched;
    }

    public void setWatched(String[] watched) {
        this.watched = watched;
    }

    public String[] getFavorites() {
        return favorites;
    }

    public void setFavorites(String[] favorites) {
        this.favorites = favorites;
    }

    public MyLists(String[] planToWatch, String[] watched, String[] favorites) {
        this.planToWatch = planToWatch;
        this.watched = watched;
        this.favorites = favorites;
    }

    public MyLists() {
    }
}
