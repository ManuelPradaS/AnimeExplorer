package com.endava.AnimeExplorer.MainController;

import com.endava.AnimeExplorer.Model.SearchingManager.Page;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import com.endava.AnimeExplorer.Model.SearchingManager.ViewProbe;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestController {


    @RequestMapping("/probe")
    public String probe() throws Exception {

        System.out.println(SearchManager.getGenres().size());

        Page currentPage = ViewProbe.probeSearch();
        return currentPage.toString();
    }
}


