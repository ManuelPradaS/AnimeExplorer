package com.endava.AnimeExplorer.MainController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestController {


    @RequestMapping("/probe")
    public String probe() throws Exception {

        return "Probe!";
    }
}


