package com.endava.AnimeExplorer;


import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class RequestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template,name));
    }

//    @RequestMapping("/probe")
//    public String probe( )throws Exception {
////
////         SearchManager.getSingleEntry(1).getData().getAttributes().getSynopsis();
//        String response=" ";
//        for (Anime anime :ExampleApplication.getAnimes()){
//            response=response+anime.getData().getAttributes().getCanonicalTitle()+"--";
//        }
//
//        return response;
//    }

    String request="?fields[anime]=id,canonicalTitle&filter[episodeCount]=26&filter[genres]=action,adventure&filter[averageRating]=70..100";


    ArrayList<String> field =
            new ArrayList<>(Arrays.asList("id","canonicalTitle"));

    String requieredFields =SearchManager.createRequieredFields(field);


    ArrayList<String> words1 =
            new ArrayList<>(Arrays.asList("action","adventure"));

    String filter1 =
           SearchManager.createSingleFilter("genres",words1);

    ArrayList<String> words2 =
            new ArrayList<>(Arrays.asList("26"));

    String filter2 =
            SearchManager.createSingleFilter("episodeCount",words2);

    ArrayList<String> words3 =
            new ArrayList<>(Arrays.asList("70..100"));

    String filter3 =
            SearchManager.createSingleFilter("averageRating",words3);

    ArrayList<String> filters =
            new ArrayList<>(Arrays.asList(filter1,filter2,filter3));


    String request2= SearchManager.makeRequest(requieredFields,filters);

    @RequestMapping("/probe")
    public String probe( )throws Exception {

        String response=" ";
          Page currentPage=SearchManager.getPageResults(request2);
          System.out.println(request2);
        return currentPage.toString();
    }


}

