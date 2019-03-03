package com.endava.AnimeExplorer.Model.SearchingManager;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewProbe {


    public static Page probeSearch()throws Exception  {

        boolean[] streamersChecks = {true, true, true, true, true, true, true, true, true, true};

        boolean[] ageRatingChecks = {false, false, false, false};

        boolean[] genresChecks = new boolean[62];
        genresChecks[2]=true;

        Page probeResult= SearchManager.requestSearch(streamersChecks,ageRatingChecks,genresChecks);

        return probeResult;

    }


}
