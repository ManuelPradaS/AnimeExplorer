package com.endava.AnimeExplorer.Model.ReportGenerator;

import com.endava.AnimeExplorer.Model.AnimeManager.AnimeFile;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class AnimeFileProcessor implements ItemProcessor<AnimeFile, AnimeFile> {

    private static final Logger log = LoggerFactory.getLogger(AnimeFileProcessor.class);

    @Override
    public AnimeFile process(AnimeFile animeFile) throws Exception {
        return animeFile;
    }
}
