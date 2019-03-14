package com.endava.AnimeExplorer.controllers;

import com.endava.AnimeExplorer.services.AnimeFileManager;
import com.endava.AnimeExplorer.model.listManager.AnimeEntry;
import com.endava.AnimeExplorer.model.listManager.AnimeToList;
import com.endava.AnimeExplorer.services.*;
import com.endava.AnimeExplorer.model.listManager.MyLists;
import com.endava.AnimeExplorer.services.BatchLauncher;
import com.endava.AnimeExplorer.model.searchManager.Page;
import com.endava.AnimeExplorer.services.SearchManager;
import com.endava.AnimeExplorer.model.userManager.ProfileInformation;
import com.endava.AnimeExplorer.model.userManager.User;
import com.endava.AnimeExplorer.model.userManager.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RequestController {

    private ISearchManager searchManager;
    private IUserManager userManager;
    private IListManager listManager;
    private IAnimeFileManager animeFileManager;
    private IBatchLauncher batchLauncher;

    @Autowired
    public RequestController(SearchManager searchManager, UserManager userManager, ListManager listManager, AnimeFileManager animeFileManager, BatchLauncher batchLauncher) {
        this.searchManager = searchManager;
        this.userManager = userManager;
        this.listManager = listManager;
        this.animeFileManager = animeFileManager;
        this.batchLauncher = batchLauncher;
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

    @PostMapping(path = "/updateUserInformation", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String>
    updateUserInformation(@RequestBody ProfileInformation currentInformation) {
        return userManager.updateInformation(currentInformation, userManager.getCurrentState().getUserId());
    }

    @GetMapping(path = "/userProfile", produces = "application/json")
    public ResponseEntity<ProfileInformation>
    viewUserProfile() {
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

    @RequestMapping("/search")
    public Page search(@RequestParam(value = "streamers", required = false) String[] streamers,
                       @RequestParam(value = "genres", required = false) String[] genres,
                       @RequestParam(value = "seasons", required = false) String[] seasons,
                       @RequestParam(value = "averageRating", required = false) Double averageRating) throws Exception {
        return searchManager.requestSearch(streamers, genres, seasons, averageRating);
    }

    @RequestMapping("/persist")
    public ResponseEntity<String> persist(@RequestParam(value = "streamers", required = false) String[] streamers,
                                          @RequestParam(value = "genres", required = false) String[] genres,
                                          @RequestParam(value = "seasons", required = false) String[] seasons,
                                          @RequestParam(value = "averageRating", required = false) Double averageRating) throws Exception {
        animeFileManager.savePageResults(searchManager.requestSearch(streamers, genres, seasons, averageRating));
        return new ResponseEntity<>("Your searching has been saved", HttpStatus.OK);
    }

    @RequestMapping("/save")
    public ResponseEntity<String> save(@RequestParam(value = "averageRating", required = false) Double averageRating) throws Exception {
        batchLauncher.launchDatabaseToCsvFileJob(averageRating);
        return new ResponseEntity<>("SAVED", HttpStatus.OK);
    }

}


