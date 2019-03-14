package com.endava.AnimeExplorer.services;

import com.endava.AnimeExplorer.model.searchManager.Anime;
import com.endava.AnimeExplorer.model.searchManager.Page;

import java.util.ArrayList;

public interface ISearchManager {

    ArrayList<String> getSeasons();
    ArrayList<String> getGenres();
    ArrayList<String> getStreamers();
    ArrayList<String> getAgeRatings();
    Anime getSingleEntry(int number) throws Exception;
    Page requestSearch(String[] streamerChecksArray, String[] genreChecksArray, String[] seasonsChecksArray, Double averageRating) throws Exception;
    String internalRequest(String[] streamerChecksArray, String[] genreChecksArray, String[] seasonsChecksArray) throws Exception;
    void init();
}
