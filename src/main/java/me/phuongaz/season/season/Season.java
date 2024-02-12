package me.phuongaz.season.season;

import lombok.Getter;
import me.phuongaz.season.Loader;
import me.phuongaz.season.api.SeasonAPI;
import me.phuongaz.season.utils.Utils;

import java.util.ArrayList;
import java.util.List;

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
    private final String season;

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

    public String getStatus(){
        if(isFirstly()){
            return "&aĐầu&r";
        }
        if(isBetween()){
            return "&eGiữa&r";
        }
        if(isLast()){
            return "&cCuối&r";
        }
        return "";
    }

    public List<String> getTopItems(int count) {
        List<String> seasonShop = Loader.getInstance().getConfig().getStringList("shop." + SeasonAPI.getNowSeason().getSeason().toLowerCase());
        List<String> maxItemsPrice = new ArrayList<>();

        seasonShop.sort((o1, o2) -> {
            String[] parts1 = o1.split(":");
            String[] parts2 = o2.split(":");
            int price1 = Integer.parseInt(parts1[2]) + Integer.parseInt(parts1[3]) + Integer.parseInt(parts1[4]);
            int price2 = Integer.parseInt(parts2[2]) + Integer.parseInt(parts2[3]) + Integer.parseInt(parts2[4]);
            return Integer.compare(price2, price1);
        });

        for (int i = 0; i < Math.min(count, seasonShop.size()); i++) {
            String item = seasonShop.get(i);
            String[] parts = item.split(":");
            maxItemsPrice.add(parts[0] + ":" + parts[1]);
        }
        return maxItemsPrice;
    }


    public boolean isBlockInSeason(int id, int count) {
        List<String> seasonShop = getTopItems(count);
        for(String i : seasonShop){
            String[] parts = i.split(":");
            if(Integer.parseInt(parts[0]) == id){
                return true;
            }
        }
        return false;
    }
}
