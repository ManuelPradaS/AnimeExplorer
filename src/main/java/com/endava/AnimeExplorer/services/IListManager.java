package com.endava.AnimeExplorer.services;

import com.endava.AnimeExplorer.model.listManager.AnimeEntry;
import com.endava.AnimeExplorer.model.listManager.AnimeToList;
import com.endava.AnimeExplorer.model.listManager.MyLists;
import com.endava.AnimeExplorer.model.searchManager.Anime;
import org.springframework.http.ResponseEntity;

public interface IListManager {

    ResponseEntity<AnimeEntry> add(AnimeToList currentRequest, int userId) throws Exception;
    ResponseEntity<AnimeEntry> addPlanTo(int userId, int animeId) throws Exception;
    ResponseEntity<AnimeEntry> addWatched(int userId, int animeId, int qualification) throws Exception;
    ResponseEntity<AnimeEntry> addFavorite(int userId, int animeId) throws Exception;
    ResponseEntity<AnimeEntry> deleteFavorite(int userId, int animeId) throws Exception;
    ResponseEntity<MyLists> getLists(int userId);
    AnimeEntry createAnimeEntry(Anime currentAnime, int userId);
}
