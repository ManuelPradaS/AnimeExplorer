package com.endava.AnimeExplorer.services;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.endava.AnimeExplorer.model.searchManager.Anime;
import com.endava.AnimeExplorer.model.searchManager.Genres;
import com.endava.AnimeExplorer.model.searchManager.Page;
import com.endava.AnimeExplorer.model.searchManager.PageGenres;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchManager implements ISearchManager {

    private static final String ROOT = "https://kitsu.io/api/edge/anime";

    private static ArrayList<String> fields = new ArrayList<>(Arrays.asList("id", "canonicalTitle"));

    private static ArrayList<String> streamers = new ArrayList<>(Arrays.asList("Hulu", "Funimation", "Crunchyroll", "Viewster", "Daisuki", "Netflix", "HIDIVE", "TubiTV", "Amazon", "YouTube"));
    private static ArrayList<String> ageRatings = new ArrayList<>(Arrays.asList("G", "PG", "R", "R18"));
    private static ArrayList<String> seasons = new ArrayList<>(Arrays.asList("spring", "summer", "fall", "winter"));
    private static ArrayList<String> genres;

    private void setGenres(ArrayList<String> genres) {
        SearchManager.genres = genres;
    }

    private String getElements(String[] stringArray) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < stringArray.length; i++) {
            String value = stringArray[i];
            if (i == stringArray.length - 1) {
                builder.append(value);
            } else {
                builder.append(value);
                builder.append(",");
            }
        }
        String elements = builder.toString();
        return elements;
    }

    private String decodeLink(String link) {

        String decodedLink = null;
        try {

            decodedLink = URLDecoder.decode(link, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            System.err.println(e);

        }
        return decodedLink;
    }

    private String createIntervalFilter(String filter, double lowerBound, double upperBound) {

        StringBuilder builder = new StringBuilder();

        builder.append("filter");
        builder.append("[");
        builder.append(filter);
        builder.append("]");
        builder.append("=");
        builder.append(lowerBound);
        builder.append("..");
        builder.append(upperBound);

        String intervalFilter = builder.toString();
        return intervalFilter;
    }

    private String createSingleFilter(String filter, List<String> words) {

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

    private String createRequiredFields() {

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

    private String makeRequest(ArrayList<String> filters) {

        String requiredFields = createRequiredFields();

        String request = "?";

        //request = request + requiredFields;

        for (String currentFilter : filters) {

            request = request + "&" + currentFilter;
        }
        return request;
    }

    private PageGenres getPageGenresResults(String link) {


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

    private ArrayList<String> addGenres(ArrayList<String> genresList, String link) {

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

    private void getAllGenres() {

        ArrayList<String> emptyList = new
                ArrayList<>();

        String link = "https://kitsu.io/api/edge/genres";

        setGenres(addGenres(emptyList, link));

    }

    private Page getPageResults(String request) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept", "application/vnd.api+json");
        headers.add("Content-Type", "application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String link = ROOT + request;
        System.out.println(link);

        ResponseEntity<Page> pageResult = restTemplate.exchange(link, HttpMethod.GET, entity, Page.class);

        return pageResult.getBody();
    }

    public ArrayList<String> getSeasons() {
        return seasons;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getStreamers() {
        return streamers;
    }

    public ArrayList<String> getAgeRatings() {
        return ageRatings;
    }

    public String encodeLink(String link) {

        String encodedLink = null;
        try {

            encodedLink = URLEncoder.encode(link, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            System.err.println(e);

        }
        return encodedLink;
    }

    public Anime getSingleEntry(int number) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept", "application/vnd.api+json");
        headers.add("Content-Type", "application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String currentUrl = ROOT + "/" + number;
        ResponseEntity<Anime> animeEntry = restTemplate.exchange(currentUrl, HttpMethod.GET, entity, Anime.class);

        return animeEntry.getBody();
    }

    public Page requestSearch(String[] streamerChecksArray, String[] genreChecksArray, String[] seasonsChecksArray, Double averageRating) throws Exception {

        boolean filterByStreamer = streamerChecksArray != null && streamerChecksArray.length > 0;
        boolean filterByGenre = genreChecksArray != null && genreChecksArray.length > 0;
        boolean filterBySeason = seasonsChecksArray != null && seasonsChecksArray.length > 0;
        boolean filterByAverageRating = averageRating != null;

        ArrayList<String> filters =
                new ArrayList<>();

        if (filterByStreamer) {
            List<String> streamerChecks = Arrays.asList(streamerChecksArray);
            filters.add(createSingleFilter("streamers", streamerChecks));
        }

        if (filterByGenre) {
            List<String> genreChecks = Arrays.asList(genreChecksArray);
            filters.add(createSingleFilter("genres", genreChecks));
        }
        if (filterBySeason) {
            List<String> seasonChecks = Arrays.asList(seasonsChecksArray);
            filters.add(createSingleFilter("season", seasonChecks));
        }
        if (filterByAverageRating) {
            filters.add(createIntervalFilter("averageRating", averageRating, 100));
        }

        String searchPage = makeRequest(filters);

        Page result = getPageResults(searchPage);

        return result;
    }

    public String internalRequest(String[] streamerChecksArray, String[] genreChecksArray, String[] seasonsChecksArray) throws Exception {

        StringBuilder builder = new StringBuilder();
        builder.append("?");

        if (streamerChecksArray.length > 0) {
            String streamersElements = getElements(streamerChecksArray);
            builder.append("streamers=");
            builder.append(streamersElements);
        }
        if (genreChecksArray.length > 0) {
            String genresElements = getElements(genreChecksArray);

            if (streamerChecksArray.length > 0) {
                builder.append("&");
            }
            builder.append("genres=");
            builder.append(genresElements);
        }
        if (seasonsChecksArray.length > 0) {
            String sesonsElements = getElements(seasonsChecksArray);

            if (genreChecksArray.length > 0 || streamerChecksArray.length > 0) {
                builder.append("&");
            }
            builder.append("seasons=");
            builder.append(sesonsElements);
        }


        String request = builder.toString();


        //String encodedRequest = "?" + SearchManager.encodeLink(request);
        return request;
    }

    public void init() {

        getAllGenres();
    }


}
