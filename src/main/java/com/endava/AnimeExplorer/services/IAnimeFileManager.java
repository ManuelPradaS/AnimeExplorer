package com.endava.AnimeExplorer.services;

import com.endava.AnimeExplorer.model.animeManager.AnimeFile;
import com.endava.AnimeExplorer.model.searchManager.AnimeSingle;
import com.endava.AnimeExplorer.model.searchManager.Page;

public interface IAnimeFileManager {
    void savePageResults(Page page);
    AnimeFile createAnimeFile(AnimeSingle currentAnime);
}
