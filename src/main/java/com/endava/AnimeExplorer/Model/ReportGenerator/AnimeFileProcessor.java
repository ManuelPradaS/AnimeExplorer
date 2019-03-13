package com.endava.AnimeExplorer.Model.ReportGenerator;

import com.endava.AnimeExplorer.Model.AnimeManager.AnimeFile;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



public class AnimeFileProcessor implements ItemProcessor<AnimeFile, AnimeFile> {

    private static final Logger log = LoggerFactory.getLogger(AnimeFileProcessor.class);


    @Value("#{jobParameters['averageRating']}")
    private Double averageRating;

    @Override
    public AnimeFile process(AnimeFile animeFile) throws Exception {
        if (averageRating==null){
            setAverageRating(0.0);
        }

        if (animeFile.getAverageRating()==null ||animeFile.getAverageRating()<averageRating){

            return null;
        }
            else{
            return animeFile;
        }


    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
