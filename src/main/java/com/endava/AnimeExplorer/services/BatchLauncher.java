package com.endava.AnimeExplorer.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BatchLauncher implements IBatchLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchLauncher.class);

    private final Job job;

    private final JobLauncher jobLauncher;

    @Autowired
    BatchLauncher(@Qualifier("databaseToCsvFileJob") Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    public void launchDatabaseToCsvFileJob(Double averageRating) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        LOGGER.info("Starting databaseToCsvFile job");

        jobLauncher.run(job, newExecution(averageRating));

        LOGGER.info("Stopping databaseToCsvFile job");
    }

    private JobParameters newExecution(Double averageRating) {
        Map<String, JobParameter> parameters = new HashMap<>();

        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);

        JobParameter processParameter = new JobParameter(averageRating);
        parameters.put("averageRating",processParameter);

        return new JobParameters(parameters);
    }

}
