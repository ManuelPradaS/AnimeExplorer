package com.endava.AnimeExplorer.MainController;

import com.endava.AnimeExplorer.Model.ListingManager.AnimeEntry;
import com.endava.AnimeExplorer.Model.ListingManager.AnimeToList;
import com.endava.AnimeExplorer.Model.ListingManager.ListManager;
import com.endava.AnimeExplorer.Model.ListingManager.MyLists;
import com.endava.AnimeExplorer.Model.SearchingManager.Page;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import com.endava.AnimeExplorer.Model.UserManager.ProfileInformation;
import com.endava.AnimeExplorer.Model.UserManager.User;
import com.endava.AnimeExplorer.Model.UserManager.UserLogin;
import com.endava.AnimeExplorer.Model.UserManager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;


@RestController
public class RequestController {


    private SearchManager searchManager;
    private UserManager userManager;
    private ListManager listManager;

    public RequestController(SearchManager searchManager, UserManager userManager, ListManager listManager) {
        this.searchManager = searchManager;
        this.userManager = userManager;
        this.listManager = listManager;
    }

    @Autowired


    @RequestMapping("/probe")
    public String probe() throws Exception {

        return "Probe!";
    }

    @RequestMapping("/search")
    public Page search(@RequestParam(value = "streamers", required = false) String[] streamers,
                       @RequestParam(value = "genres", required = false) String[] genres,
                        @RequestParam(value = "seasons", required = false) String[] seasons) throws Exception {

        return searchManager.requestSearch(streamers, genres,seasons);
    }


    @PostMapping(path = "/createUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody UserLogin newUser) {

        return userManager.addUser(newUser);
    }


    @PostMapping(path = "/logIn", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserLogin> logUser(@RequestBody UserLogin currentUser) {

        return userManager.logUser(currentUser);
    }

    @PostMapping(path = "/logOut")
    public String logOut() {

        return userManager.logOut();
    }

    @PostMapping(path = "/updateInformation", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String>
    updateInformation(@RequestBody ProfileInformation currentInformation) {

        return userManager.updateInformation(currentInformation, userManager.getCurrentState().getUserId());
    }

    @GetMapping(path = "/profile", produces = "application/json")
    public ResponseEntity<ProfileInformation>
    viewProfile() {

        return userManager.viewProfile(userManager.getCurrentState().getUserId());
    }

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AnimeEntry>
    add(@RequestBody AnimeToList currentInformation) throws Exception {

        return listManager.add(currentInformation, userManager.getCurrentState().getUserId());
    }

    @GetMapping(path = "/myLists", produces = "application/json")
    public ResponseEntity<MyLists>
    viewLists() {

        return listManager.getLists(userManager.getCurrentState().getUserId());
    }

}


