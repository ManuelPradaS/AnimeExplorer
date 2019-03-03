package com.endava.AnimeExplorer.Model.SearchingManager;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewProbe {



     static ArrayList<String> field =
            new ArrayList<>(Arrays.asList("id","canonicalTitle"));

     String requieredFields = SearchManager.createRequiredFields(field);


     static ArrayList<String> words1 =
            new ArrayList<>(Arrays.asList("action","adventure"));

     static String filter1 =
            SearchManager.createSingleFilter("genres",words1);

     static ArrayList<String> words2 =
            new ArrayList<>(Arrays.asList("26"));

     static  String filter2 =
            SearchManager.createSingleFilter("episodeCount",words2);

     static ArrayList<String> words3 =
            new ArrayList<>(Arrays.asList("70..100"));

     static  String filter3 =
            SearchManager.createSingleFilter("averageRating",words3);

     static ArrayList<String> filters =
            new ArrayList<>(Arrays.asList(filter1,filter2,filter3));


    public String request2= SearchManager.makeRequest(requieredFields,filters);


    Boolean[] probee = { true,true,true,true,true,true,true,true,true,true};

    public String searchProbe(){

      return   SearchManager.filterByStreamers(probee);

    }


}
