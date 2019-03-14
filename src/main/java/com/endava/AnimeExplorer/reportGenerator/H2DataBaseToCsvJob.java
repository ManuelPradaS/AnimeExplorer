package com.endava.AnimeExplorer.reportGenerator;

import com.endava.AnimeExplorer.model.animeManager.AnimeFile;
import com.endava.AnimeExplorer.repositories.AnimeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@Configuration
@EnableBatchProcessing
public class H2DataBaseToCsvJob {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean(name = "animeFileReader")
    @StepScope
    public RepositoryItemReader<AnimeFile> reader() {
        RepositoryItemReader<AnimeFile> reader = new RepositoryItemReader<>();
        reader.setRepository(animeRepository);
        reader.setMethodName("findAll");
        reader.setSort(Collections.singletonMap("canonicalTitle", Sort.Direction.ASC));
        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<AnimeFile, AnimeFile> databaseCsvItemProcessor() {
        return new AnimeFileProcessor();
    }


    private Resource outputResource = new FileSystemResource("output/animeReport.csv");

    @Bean
    public FlatFileItemWriter<AnimeFile> writer() {

        FlatFileItemWriter<AnimeFile> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);
        writer.setAppendAllowed(true);
        HeaderWriter headerWriter = new HeaderWriter("TITLE,AVERAGE RATING, START DATE, END_DATE, AGE_RATING");
        writer.setHeaderCallback(headerWriter);

        writer.setLineAggregator(new DelimitedLineAggregator<AnimeFile>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<AnimeFile>() {
                    {
                        setNames(new String[]{"canonicalTitle", "averageRating", "startDate", "endDate", "ageRating"});
                    }
                });
            }
        });
        return writer;
    }

    @Bean
    public Step databaseToCsvFileStep() {
        return stepBuilderFactory.get("databaseToCsvFileStep")
                .<AnimeFile, AnimeFile>chunk(1)
                .reader(reader())
                .processor(databaseCsvItemProcessor())
                .writer(writer())
                .build();
    }


    @Bean
    public Job databaseToCsvFileJob() {
        return jobBuilderFactory.get("databaseToCsvFileJob")
                .incrementer(new RunIdIncrementer())
                .flow(databaseToCsvFileStep())
                .end()
                .build();
    }

}