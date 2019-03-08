package com.endava.AnimeExplorer.Model.ListingManager;

import com.endava.AnimeExplorer.Model.SearchingManager.Anime;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import com.endava.AnimeExplorer.Repositories.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListManager {

    private SearchManager searchManager;

    @Autowired
    public ListManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    @Autowired
    private ListRepository listRepository;


    public ResponseEntity<AnimeEntry> add(AnimeToList currentRequest, int userId) throws Exception {

        int requestCase = currentRequest.getRequest();
        switch (requestCase) {
            case 1:
                return addPlanTo(userId, currentRequest.getAnimeId());

            case 2:
                return addWatched(userId, currentRequest.getAnimeId(), currentRequest.getQualification());

            case 3:
                return addFavorite(userId, currentRequest.getAnimeId());


        }
        AnimeEntry empty = new AnimeEntry();
        return new ResponseEntity<>(empty, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<AnimeEntry> addPlanTo(int userId, int animeId) throws Exception {

        Anime currentAnime = searchManager.getSingleEntry(animeId);

        Optional<AnimeEntry> result = listRepository.findByUserIdAndAnimeId(userId, animeId);

        boolean exists = result.isPresent();

        if (exists) {

            AnimeEntry currentEntry = result.get();

            return new ResponseEntity<>(currentEntry, HttpStatus.INTERNAL_SERVER_ERROR);

        } else {
            AnimeEntry newEntry = createAnimeEntry(currentAnime, userId);
            newEntry.setPlanToWatch(true);
            listRepository.save(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.OK);
        }

    }

    public ResponseEntity<AnimeEntry> addWatched(int userId, int animeId, int qualification) throws Exception {

        Anime currentAnime = searchManager.getSingleEntry(animeId);

        Optional<AnimeEntry> result = listRepository.findByUserIdAndAnimeId(userId, animeId);

        boolean exists = result.isPresent();

        if (exists) {
            AnimeEntry currentEntry = result.get();

            if (currentEntry.isWatched() || currentEntry.isFavorite()) {
                return new ResponseEntity<>(currentEntry, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                currentEntry.setQualification(qualification);
                currentEntry.setPlanToWatch(false);
                listRepository.save(currentEntry);
                return new ResponseEntity<>(currentEntry, HttpStatus.OK);
            }


        } else {
            AnimeEntry newEntry = createAnimeEntry(currentAnime, userId);
            newEntry.setWatched(true);
            newEntry.setQualification(qualification);
            listRepository.save(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.OK);
        }


    }

    public ResponseEntity<AnimeEntry> addFavorite(int userId, int animeId) throws Exception {
        Anime currentAnime = searchManager.getSingleEntry(animeId);

        Optional<AnimeEntry> result = listRepository.findByUserIdAndAnimeId(userId, animeId);
        boolean exists = result.isPresent();

        if (exists) {

            AnimeEntry currentEntry = result.get();
            if (currentEntry.isWatched()) {
                AnimeEntry newEntry = createAnimeEntry(currentAnime, userId);
                newEntry.setFavorite(true);
                listRepository.save(newEntry);
                return new ResponseEntity<>(newEntry, HttpStatus.OK);
            } else {
                AnimeEntry newEntry = createAnimeEntry(currentAnime, userId);
                return new ResponseEntity<>(newEntry, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            AnimeEntry newEntry = createAnimeEntry(currentAnime, userId);
            return new ResponseEntity<>(newEntry, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private AnimeEntry createAnimeEntry(Anime currentAnime, int userId) {
        AnimeEntry result = new AnimeEntry();

        result.setUserId(userId);
        result.setAnimeId(currentAnime.getData().getId());
        result.setTitle(currentAnime.getData().getAttributes().getCanonicalTitle());

        return result;
    }


}
