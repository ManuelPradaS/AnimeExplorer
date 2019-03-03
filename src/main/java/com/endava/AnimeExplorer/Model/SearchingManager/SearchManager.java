package com.endava.AnimeExplorer.Model.SearchingManager;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchManager {

    static final String root = "https://kitsu.io/api/edge/anime";


    public static Anime getSingleEntry(int number) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept", "application/vnd.api+json");
        headers.add("Content-Type", "application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String currentUrl = root + "/" + number;
        ResponseEntity<Anime> animeEntry = restTemplate.exchange(currentUrl, HttpMethod.GET, entity, Anime.class);

        return animeEntry.getBody();
    }

    public static Page getPageResults(String request) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept", "application/vnd.api+json");
        headers.add("Content-Type", "application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String link = root + request;

        ResponseEntity<Page> pageResult = restTemplate.exchange(link, HttpMethod.GET, entity, Page.class);

        return pageResult.getBody();
    }

    public static String createSingleFilter(String filter, List<String> words) {

        String singleFilter = "filter" + "[" + filter + "]" + "=";

        for (int i = 0; i < words.size(); i++) {

            String currentWord = words.get(i);

            if (i == words.size() - 1) {

                singleFilter = singleFilter + currentWord;

            } else {

                singleFilter = singleFilter + currentWord + ",";
            }

        }

        return singleFilter;

    }

    public static String createRequiredFields(ArrayList<String> fields) {

        String requiredFields = "fields[anime]=";

        for (int i = 0; i < fields.size(); i++) {

            String currentField = fields.get(i);

            if (i == fields.size() - 1) {

                requiredFields = requiredFields + currentField;

            } else {

                requiredFields = requiredFields + currentField + ",";
            }

        }

        return requiredFields;

    }

    public static String makeRequest(String fields, ArrayList<String> filters) {

        String request = "?";

        request = request + fields;

        for (String currentFilter : filters) {

            request = request + "&" + currentFilter;
        }
        return request;
    }

    public static String filterByStreamers(Boolean[] streamerChecks) {

        ArrayList<String> streamers =
                new ArrayList<>(Arrays.asList("Hulu", "Funimation", "Crunchyroll", "Viewster", "Daisuki", "Netflix", "HIDIVE", "TubiTV", "Amazon", "YouTube"));


        ArrayList<String> appliedStreamers =
                new ArrayList<>();

        for (int i = 0; i < streamers.size(); i++) {

            if (streamerChecks[i]) {
                appliedStreamers.add(streamers.get(i));
            } else {
            }
        }

        String filterStreamer = createSingleFilter("streamers", appliedStreamers);

        return filterStreamer;
    }

    private static PageGenres getPageGenresResults(String link) {


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        //headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept", "application/vnd.api+json");
        headers.add("Content-Type", "application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<PageGenres> genresResult = restTemplate.exchange(link, HttpMethod.GET, entity, PageGenres.class);

        return genresResult.getBody();
    }


    private static ArrayList<String> addGenres(ArrayList<String> genresList, String link) {

        PageGenres currentPage = getPageGenresResults(link);

        for (Genres currentGenre : currentPage.getData()) {

            System.out.println(currentGenre.getAttributes().getName());
            genresList.add(currentGenre.getAttributes().getName());
        }



        String next=currentPage.getLinks().getNext();
        System.out.println(next);

        PageGenres nextPage = getPageGenresResults(next);

        for (Genres currentGenre : nextPage.getData()) {

            System.out.println(currentGenre.getAttributes().getName());

        }

//
//        PageGenres currentPage = firstPage;
//
//        String next=currentPage.getLinks().getNext();
//
//        System.out.println(next);
//
//
//
//        for (Genres currentGenre : getPageGenresResults(next).getData()) {
//
//            System.out.println(currentGenre.getAttributes().getName());
//
//            genresList.add(currentGenre.getAttributes().getName());
//
//        }
//
//        String next=currentPage.getLinks().getNext();
//
//        while (currentPage.getLinks().getNext() != null) {
//
//
//            currentPage = getPageGenresResults(next);
//
//            for (Genres currentGenre : currentPage.getData()) {
//                  System.out.println(currentGenre.getAttributes().getName());
//                genresList.add(currentGenre.getAttributes().getName());
//            }
//
//            next=currentPage.getLinks().getNext();
//
//        }


        return genresList;

    }

    public static ArrayList<String> getAllGenres() {

        ArrayList<String> emptyList = new
                ArrayList<>();

        String link = "https://kitsu.io/api/edge/genres";

//        link="https://kitsu.io/api/edge/genres?page%5Blimit%5D=10&page%5Boffset%5D=10";

        ArrayList<String> genresList = addGenres(emptyList, link);

        return genresList;


    }


}
