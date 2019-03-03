package com.endava.AnimeExplorer.MainController;


import com.endava.AnimeExplorer.Model.SearchingManager.Page;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class RequestController {

    private static final String template = "Hello, %s!";


    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template,name);
    }

    String request="?fields[anime]=id,canonicalTitle&filter[episodeCount]=26&filter[genres]=action,adventure&filter[averageRating]=70..100";



    @RequestMapping("/probe")
    public String probe( )throws Exception {


        ArrayList<String> field =
                new ArrayList<>(Arrays.asList("id","canonicalTitle"));

        String requieredFields = SearchManager.createRequiredFields(field);

        Boolean[] probee = { true,true,true,true,true,true,true,true,true,true};

         String filter1 =
                SearchManager.filterByStreamers(probee);


         ArrayList<String> filters =
                new ArrayList<>(Arrays.asList(filter1));

     String searchPage= SearchManager.makeRequest(requieredFields,filters);


          Page currentPage=SearchManager.getPageResults(searchPage);
          System.out.println(searchPage);



        System.out.println(SearchManager.getAllGenres());


        return currentPage.toString();
    }


}

