package com.endava.AnimeExplorer.MainController;

import com.endava.AnimeExplorer.Model.SearchingManager.Page;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import com.endava.AnimeExplorer.Model.UserManager.User;
import com.endava.AnimeExplorer.Model.UserManager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;


@RestController
public class RequestController {


    private SearchManager searchManager;
    private UserManager userManager;

    @Autowired
    public RequestController(SearchManager searchManager,UserManager userManager) {
        this.searchManager = searchManager;
        this.userManager= userManager;
    }


    @RequestMapping("/probe")
    public String probe() throws Exception {

        return "Probe!";
    }

    @RequestMapping("/search")
    public Page search(@RequestParam(value = "streamers",required = false) String[] streamers,
                       @RequestParam(value = "genres",required = false) String[] genres)throws  Exception{


        System.out.println(Arrays.toString(streamers));

        System.out.println(Arrays.toString(genres));

        return searchManager.requestSearch(streamers,genres);
    }


    @PostMapping(path="/createUser", consumes = "application/json", produces = "application/json")
    public User addUser(@RequestBody User newUser){

        return userManager.addUser(newUser);
    }
}


