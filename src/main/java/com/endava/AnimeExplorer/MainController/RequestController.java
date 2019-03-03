package com.endava.AnimeExplorer.MainController;


import com.endava.AnimeExplorer.Model.SearchingManager.Page;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import com.endava.AnimeExplorer.Model.SearchingManager.ViewProbe;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class RequestController {

    private static final String template = "Hello, %s!";

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }

    @RequestMapping("/probe")
    public String probe() throws Exception {

        System.out.println(SearchManager.getGenres().size());


        Page currentPage = ViewProbe.probeSearch();

        return currentPage.toString();
    }


}

