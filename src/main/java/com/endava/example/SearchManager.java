package com.endava.example;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchManager {

    static final String root="https://kitsu.io/api/edge/anime";
    static final int animesCount=14215;

    public static Anime getSingleEntry(int number)throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept","application/vnd.api+json");
        headers.add("Content-Type","application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String currentUrl = root+"/" +number;
        ResponseEntity<Anime> animeEntry = restTemplate.exchange(currentUrl, HttpMethod.GET, entity, Anime.class);
//        System.out.println(animeEntry.getBody());

        return animeEntry.getBody();
    }

    public static ArrayList<Anime> getAllAnimes()throws Exception{
        ArrayList<Anime> allAnimes= new ArrayList<>();

        for (int i=1;i<10;i++)
        {
            System.out.println(i);
            try {
                allAnimes.add(getSingleEntry(i));
            }catch(Exception e){}
        }
        return allAnimes;
    }




    public static Page getPageResults(String request) throws Exception{

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept","application/vnd.api+json");
        headers.add("Content-Type","application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String link=root+request;

        ResponseEntity<Page> pageResult = restTemplate.exchange(link, HttpMethod.GET, entity, Page.class);

        return pageResult.getBody();
    }

}
