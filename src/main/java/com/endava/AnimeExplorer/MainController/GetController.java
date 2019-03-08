package com.endava.AnimeExplorer.MainController;

import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GetController {

    private SearchManager searchManager;

    @Autowired
    public GetController(SearchManager searchManager) {
        this.searchManager = searchManager;
        searchManager.init();
    }


    @ModelAttribute("genres")
    public String[] getGenres() {

        String[] genresArray = searchManager.getGenres().stream()
                .toArray(String[]::new);
        return genresArray;
    }

    @ModelAttribute("streamers")
    public String[] getStreamers() {

        String[] streamersArray = searchManager.getStreamers().stream()
                .toArray(String[]::new);
        return streamersArray;
    }

    @ModelAttribute("ageRatings")
    public String[] getAgeRatings() {

        String[] ageRatingsArray = searchManager.getAgeRatings().stream()
                .toArray(String[]::new);
        return ageRatingsArray;
    }
    @ModelAttribute("seasons")
    public String[] getSeasons() {

        String[] seasonsArray = searchManager.getSeasons().stream()
                .toArray(String[]::new);
        return seasonsArray;
    }


    @GetMapping("/searchForm")
    public String form(Model model) {
        model.addAttribute("command", new FormCommand());

        return "searchForm";
    }


    @PostMapping("/searchForm")
    public String formPost(
            @ModelAttribute("command") FormCommand command,
            // WARN: BindingResult *must* immediately follow the Command.
            // https://stackoverflow.com/a/29883178/1626026
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra) throws Exception {


        String request = searchManager.internalRequest(command.getStreamers(), command.getGenres(),command.getSeasons());
        System.out.println(request+"<-----");

        if (bindingResult.hasErrors()) {
            return "searchForm";
        }

        ra.addFlashAttribute("command", command);


        return "redirect:/search"+request;
    }

    @GetMapping("/searchingResult")
    public String foormResult(
            @ModelAttribute("command") FormCommand command, Model model) {

        return "searchingResult";

    }


}

