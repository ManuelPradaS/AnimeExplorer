package com.endava.AnimeExplorer.MainController;


import com.endava.AnimeExplorer.Model.SearchingManager.Anime;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import java.util.*;


@SpringBootApplication
@ComponentScan({"com.endava.AnimeExplorer"})
public class ExampleApplication {

	public static Logger getLog() {
		return log;
	}

	public static void main(String[] args) {

		SpringApplication.run(ExampleApplication.class, args);

	}

	private static final Logger log = LoggerFactory.getLogger(ExampleApplication.class);

	@Bean
	public static RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			System.out.println("Ready");
		};
	}









}
