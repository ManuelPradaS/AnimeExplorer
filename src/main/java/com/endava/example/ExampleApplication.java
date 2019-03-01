package com.endava.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import java.util.*;


@SpringBootApplication
public class ExampleApplication {

	static private ArrayList<Anime> animes;

	public static ArrayList<Anime> getAnimes() {
		return animes;
	}

	public static void setAnimes(ArrayList<Anime> animes) {
		ExampleApplication.animes = animes;
	}

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
			animes=SearchManager.getAllAnimes();
			System.out.println("done");
		};
	}









}
