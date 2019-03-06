package com.endava.AnimeExplorer.Model.SearchingManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchManager {

    private static final String root = "https://kitsu.io/api/edge/anime";

    private static ArrayList<String> fields = new ArrayList<>(Arrays.asList("id", "canonicalTitle"));

    private static ArrayList<String> streamers = new ArrayList<>(Arrays.asList("Hulu", "Funimation", "Crunchyroll", "Viewster", "Daisuki", "Netflix", "HIDIVE", "TubiTV", "Amazon", "YouTube"));
    private static ArrayList<String> ageRatings = new ArrayList<>(Arrays.asList("G", "PG", "R", "R18"));
    private static ArrayList<String> genres;

    public  ArrayList<String> getGenres() {
        return genres;
    }

    private  void setGenres(ArrayList<String> genres) {
        SearchManager.genres = genres;
    }

    public  ArrayList<String> getStreamers() {
        return streamers;
    }

    public  ArrayList<String> getAgeRatings() {
        return ageRatings;
    }

    private  String decodeLink(String link) {

        String decodedLink = null;
        try {

            decodedLink = URLDecoder.decode(link, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            System.err.println(e);

        }
        return decodedLink;
    }

    public  String encodeLink(String link) {

        String encodedLink = null;
        try {

            encodedLink = URLEncoder.encode(link, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            System.err.println(e);

        }
        return encodedLink;
    }


    private  boolean anyTrue(boolean[] array) {
        for (boolean element : array) if (element) return true;
        return false;
    }


    public  Anime getSingleEntry(int number) throws Exception {
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

    public  Page getPageResults(String request) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept", "application/vnd.api+json");
        headers.add("Content-Type", "application/vnd.api+json");

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String link = root + request;
        System.out.println(link);

        ResponseEntity<Page> pageResult = restTemplate.exchange(link, HttpMethod.GET, entity, Page.class);

        return pageResult.getBody();
    }


    private  String createSingleFilter(String filter, List<String> words) {

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

    private  String createRequiredFields() {

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

    private  String makeRequest(ArrayList<String> filters) {

        String requiredFields = createRequiredFields();

        String request = "?";

        request = request + requiredFields;

        for (String currentFilter : filters) {

            request = request + "&" + currentFilter;
        }
        return request;
    }

    private  PageGenres getPageGenresResults(String link) {


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

    private  ArrayList<String> addGenres(ArrayList<String> genresList, String link) {

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

    private  void getAllGenres() {

        ArrayList<String> emptyList = new
                ArrayList<>();

        String link = "https://kitsu.io/api/edge/genres";

        setGenres(addGenres(emptyList, link));

    }

    public Page requestSearch(String[] streamerChecksArray, String[] genreChecksArray) throws Exception{

        // String[] ageRatingChecksArray

       // List<String> ageRatingChecks = Arrays.asList( ageRatingChecksArray );

        boolean filterByStreamer= streamerChecksArray.length >0;
        //boolean filterByAgeRating=ageRatingChecks.size()>0;
        boolean filterByGenre=genreChecksArray.length >0;

        ArrayList<String> filters =
                new ArrayList<>();

        if (filterByStreamer){
            List<String> streamerChecks = Arrays.asList(streamerChecksArray);
           filters.add(createSingleFilter("streamers", streamerChecks));
        }
//        if (false){
//            filters.add(SearchManager.createSingleFilter("ageRatings", ageRatingChecks));
//        }
        if (filterByGenre){
            List<String> genreChecks = Arrays.asList(genreChecksArray);
            filters.add(createSingleFilter("genres", genreChecks));
        }

        String searchPage = makeRequest(filters);

        Page result =getPageResults(searchPage);

        return result;
    }

    public  String internalRequest(String[] streamerChecksArray, String[] genreChecksArray) throws Exception{


        String streamersElements=getElements(streamerChecksArray);
        String genresElements=getElements(genreChecksArray);

        String request = "?"+"streamers=" +streamersElements  + "&" + "genres=" + genresElements;

        //String encodedRequest = "?" + SearchManager.encodeLink(request);

        return request;
    }

    private  String getElements(String[] stringArray){

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i <stringArray.length ; i++) {
            String value=stringArray[i];
            if(i==stringArray.length-1){
                builder.append(value);
            }
            else{
                builder.append(value);
                builder.append(",");
            }
        }
        String elements = builder.toString();
        return elements;
    }

    public  void init() {

        getAllGenres();
    }


}
