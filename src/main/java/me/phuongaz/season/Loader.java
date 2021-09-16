package me.phuongaz.season;

import cn.nukkit.plugin.PluginBase;
import me.phuongaz.season.command.ShopCommand;
import me.phuongaz.season.season.Season;
import me.phuongaz.season.utils.Utils;

public class Loader extends PluginBase{

    private static Loader _instace;
    private Season season;

    @Override
    public void onLoad(){
        _instace = this;
    }
    
    @Override
    public void onEnable(){
        this.loadSeason();
        Utils.runSeason();
        saveDefaultConfig();
        getServer().getCommandMap().register("Season", new ShopCommand());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    public static Loader getInstance(){
        return _instace;
    }

    public void loadSeason(){
        String season = getConfig().getString("season.name");
        this.season = new Season(season);
    }

    public Season getSeason(){
        return this.season;
    }

}
