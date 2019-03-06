package com.endava.AnimeExplorer.MainController;

import com.endava.AnimeExplorer.Model.SearchingManager.Page;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
public class RequestController {



    @RequestMapping("/probe")
    public String probe() throws Exception {

        return "Probe!";
    }

    @RequestMapping("/search")
    public Page search(@RequestParam(value = "streamers",required = false) String[] streamers,
                       @RequestParam(value = "genres",required = false) String[] genres)throws  Exception{


        //SearchManager.requestSearch(streamers,genres);

        System.out.println(Arrays.toString(streamers));

        System.out.println(Arrays.toString(genres));


        return SearchManager.requestSearch(streamers,genres) ;
    }
}


