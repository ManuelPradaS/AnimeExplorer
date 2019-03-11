package com.endava.AnimeExplorer.Model.AnimeManager;

import com.endava.AnimeExplorer.Model.SearchingManager.AnimeSingle;
import com.endava.AnimeExplorer.Model.SearchingManager.AttributesSingle;

import com.endava.AnimeExplorer.Model.SearchingManager.Page;
import com.endava.AnimeExplorer.Repositories.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimeFileManager {

    @Autowired
    private AnimeRepository animeRepository;

    public void savePageResults(Page page) {
        for (AnimeSingle currentEntry : page.getData()) {

            animeRepository.save(createAnimeFile(currentEntry));
        }

    }

    private AnimeFile createAnimeFile(AnimeSingle currentAnime) {
        AnimeFile result = new AnimeFile();

        AttributesSingle currentAttributes = currentAnime.getAttributes();
        result.setCanonicalTitle(currentAttributes.getCanonicalTitle());
        result.setAverageRating(currentAttributes.getAverageRating());
        result.setStartDate(currentAttributes.getStartDate());
        result.setEndDate(currentAttributes.getEndDate());
        result.setAgeRating(currentAttributes.getAgeRating());
        return result;
    }

}
