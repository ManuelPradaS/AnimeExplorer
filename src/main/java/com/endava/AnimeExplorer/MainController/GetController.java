package com.endava.AnimeExplorer.MainController;

import com.endava.AnimeExplorer.Model.SearchingManager.Page;
import com.endava.AnimeExplorer.Model.SearchingManager.SearchManager;
import com.endava.AnimeExplorer.Model.SearchingManager.AnimeSingle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

import static com.endava.AnimeExplorer.Model.SearchingManager.SearchManager.internalRequest;


@Controller
public class GetController {

    @ModelAttribute("genres")
    public String[] getGenres() {

        String[] genresArray = SearchManager.getGenres().stream()
                .toArray(String[]::new);
        return genresArray;
    }

    @ModelAttribute("streamers")
    public String[] getStreamers() {

        String[] streamersArray = SearchManager.getStreamers().stream()
                .toArray(String[]::new);
        return streamersArray;
    }

    @ModelAttribute("ageRatings")
    public String[] getAgeRatings() {

        String[] ageRatingsArray = SearchManager.getAgeRatings().stream()
                .toArray(String[]::new);
        return ageRatingsArray;
    }


    @GetMapping("/searchForm")
    public String form(Model model) {
        model.addAttribute("command", new FormCommand());

        return "searchForm";
    }

    Page prueba;

    @PostMapping("/searchForm")
    public String formPost(
            @ModelAttribute("command") FormCommand command,
            // WARN: BindingResult *must* immediately follow the Command.
            // https://stackoverflow.com/a/29883178/1626026
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra) throws Exception {


        String request = SearchManager.internalRequest(command.getStreamers(), command.getGenres());
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

