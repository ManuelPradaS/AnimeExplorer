package com.endava.AnimeExplorer.services;

import com.endava.AnimeExplorer.model.animeManager.AnimeFile;
import com.endava.AnimeExplorer.model.searchManager.AnimeSingle;
import com.endava.AnimeExplorer.model.searchManager.AttributesSingle;

import com.endava.AnimeExplorer.model.searchManager.Page;
import com.endava.AnimeExplorer.repositories.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnimeFileManager implements IAnimeFileManager {

    @Autowired
    private AnimeRepository animeRepository;

    public void savePageResults(Page page) {
        for (AnimeSingle currentEntry : page.getData()) {
            animeRepository.save(createAnimeFile(currentEntry));
        }
    }

    public AnimeFile createAnimeFile(AnimeSingle currentAnime) {
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
