package me.phuongaz.season.season;

import lombok.Getter;
import me.phuongaz.season.utils.Utils;

public class Season {

    enum Seasons{
        SPRING("SPRING", "Mùa Xuân"),
        SUMMER("SUMMER", "Mùa Hạ"),
        FALL("FALL", "Mùa Thu"),
        WINTER("WINTER", "Mùa Đông");        
    
        @Getter
        private String season;
        @Getter
        private String name;

        Seasons(String season, String name){
            this.season = season;
            this.name = name;
        }
    }

    @Getter
    private String season;

    public Season(String season){
        this.season = season;
    }

    public String getName(){
        return getSeasonById(this.season.toUpperCase());
    }

    private String getSeasonById(String season){
        return Seasons.valueOf(season.toUpperCase()).getName();
    }

    public Season getNextSeason(){
        switch (this.season) {
            case "spring":
                return new Season("summer");
            case "summer":
                return new Season("fall");
            case "fall":
                return new Season("winter");       
            default:
                return new Season("spring");
        }
    }

    public boolean isFirstly(){
        String date = Utils.getDay();
        return (date.equals("Monday") || date.equals("Tuesday"));
    }

    public boolean isBetween(){
        String date = Utils.getDay();
        return (date.equals("Wednesday") || date.equals("Thursday") || date.equals("Friday"));
    }

    public boolean isLast(){
        String date = Utils.getDay();
        return (date.equals("Saturday") || date.equals("Sunday"));
    }
}
