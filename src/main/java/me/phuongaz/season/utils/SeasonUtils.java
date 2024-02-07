package me.phuongaz.season.utils;

import cn.nukkit.item.Item;
import me.phuongaz.season.api.SeasonAPI;
import me.phuongaz.season.season.Season;

import java.util.ArrayList;
import java.util.List;

public class SeasonUtils {

    public static List<String> getSeasonBlocksName() {
        Season season = SeasonAPI.getNowSeason();

        List<String> blocks = season.getTopItems(3);

        List<String> blockNames = new ArrayList<>();

        for (String block : blocks) {
            String[] blockData = block.split(":");
            System.out.println(blockData[0]);
            blockNames.add(Item.get(Integer.parseInt(blockData[0])).getName());
        }
        return blockNames;
    }

    public static List<Integer> getSeasonBlocksPrice() {
        Season season = SeasonAPI.getNowSeason();

        List<String> blocks = season.getTopItems(3);

        List<Integer> blockPrices = new ArrayList<>();

        for (String block : blocks) {
            String[] blockData = block.split(":");
            blockPrices.add(Integer.parseInt(blockData[1]));
        }
        return blockPrices;
    }
}