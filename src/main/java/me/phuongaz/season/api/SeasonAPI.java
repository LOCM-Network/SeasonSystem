package me.phuongaz.season.api;

import me.phuongaz.season.Loader;
import me.phuongaz.season.season.Season;

public class SeasonAPI {

    public static Season getNowSeason(){
        return Loader.getInstance().getSeason();
    }
}
