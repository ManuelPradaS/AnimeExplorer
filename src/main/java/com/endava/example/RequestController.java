package com.endava.example;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/probe")
    public String probe( )throws Exception {

        String response=" ";
          Page currentPage=SearchManager.getPageResults(request);
        return currentPage.toString();
    }


}

