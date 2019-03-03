package com.endava.AnimeExplorer.Model.SearchingManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchManager {

    private static final String root = "https://kitsu.io/api/edge/anime";

    private static ArrayList<String> fields = new ArrayList<>(Arrays.asList("id", "canonicalTitle"));

    private static ArrayList<String> streamers = new ArrayList<>(Arrays.asList("Hulu", "Funimation", "Crunchyroll", "Viewster", "Daisuki", "Netflix", "HIDIVE", "TubiTV", "Amazon", "YouTube"));
    private static ArrayList<String> ageRatings = new ArrayList<>(Arrays.asList("G", "PG", "R", "R18"));
    private static ArrayList<String> genres;

    public static ArrayList<String> getGenres() {
        return genres;
    }

    private static void setGenres(ArrayList<String> genres) {
        SearchManager.genres = genres;
    }

    private static String decodeLink(String link) {

        String decodedLink = null;
        try {

            decodedLink = URLDecoder.decode(link, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            System.err.println(e);

        }
        return decodedLink;
    }

    private static boolean anyTrue(boolean[] array) {
        for (boolean element : array) if (element) return true;
        return false;
    }

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

    public static String createRequiredFields() {

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

    public static String makeRequest(ArrayList<String> filters) {

        String requiredFields = SearchManager.createRequiredFields();

        String request = "?";

        request = request + requiredFields;

        for (String currentFilter : filters) {

            request = request + "&" + currentFilter;
        }
        return request;
    }

    public static String filterBy(String filter, boolean[] checks) {


        ArrayList<String> words =
                new ArrayList<>();

        switch (filter) {

            case "streamers": {
                words = streamers;
                break;
            }

            case "genres": {
                words = genres;
                break;
            }


            case "ageRatings": {
                words = ageRatings;
                break;
            }


        }


        ArrayList<String> appliedWords =
                new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {

            if (checks[i]) {
                appliedWords.add(words.get(i));
            } else {
            }
        }

        String filterCategory = createSingleFilter(filter, appliedWords);

        return filterCategory;
    }

    private static PageGenres getPageGenresResults(String link) {


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept", "application/vnd.api+json");
        headers.add("Content-Type", "application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<PageGenres> genresResult = restTemplate.exchange(link, HttpMethod.GET, entity, PageGenres.class);

        return genresResult.getBody();
    }

    private static ArrayList<String> addGenres(ArrayList<String> genresList, String link) {

        PageGenres currentPage = getPageGenresResults(link);

        for (Genres currentGenre : currentPage.getData()) {

            genresList.add(currentGenre.getAttributes().getName());
        }

        String next;

        while (currentPage.getLinks().getNext() != null) {

            next = decodeLink(currentPage.getLinks().getNext());
            currentPage = getPageGenresResults(next);

            for (Genres currentGenre : currentPage.getData()) {
                genresList.add(currentGenre.getAttributes().getName());
            }
        }
        return genresList;
    }

    private static void getAllGenres() {

        ArrayList<String> emptyList = new
                ArrayList<>();

        String link = "https://kitsu.io/api/edge/genres";

        setGenres(addGenres(emptyList, link));

    }

    public static Page requestSearch(boolean[] streamerChecks, boolean[] ageRatingChecks, boolean[] genreChecks) throws Exception{

        boolean filterByStreamer=anyTrue(streamerChecks);
        boolean filterByAgeRating=anyTrue(ageRatingChecks);
        boolean filterByGenre=anyTrue(genreChecks);

        ArrayList<String> filters =
                new ArrayList<>();

        if (filterByStreamer){
           filters.add(SearchManager.filterBy("streamers", streamerChecks));
        }
        if (filterByAgeRating){
            filters.add(SearchManager.filterBy("ageRatings", ageRatingChecks));
        }
        if (filterByGenre){
            filters.add(SearchManager.filterBy("genres", genreChecks));
        }

        String searchPage = SearchManager.makeRequest(filters);

        System.out.println(searchPage);
        Page resultPage=SearchManager.getPageResults(searchPage);

        return resultPage;
    }


    public static void init() {

        getAllGenres();
    }


}
