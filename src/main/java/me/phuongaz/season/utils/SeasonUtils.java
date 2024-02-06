package me.phuongaz.season.utils;

import cn.nukkit.block.Block;
import me.phuongaz.season.api.SeasonAPI;
import me.phuongaz.season.season.Season;

import java.util.ArrayList;
import java.util.List;

public class SeasonUtils {

    public static List<String> getSeasonBlocksName() {
        Season season = SeasonAPI.getNowSeason();

        List<String> blocks = season.getSeasonBlocks();

        List<String> blockNames = new ArrayList<>();

        for (String block : blocks) {
            String[] blockData = block.split(":");
            blockNames.add(Block.get(Integer.parseInt(blockData[0])).getName());
        }
        return blockNames;
    }

    public static List<Integer> getSeasonBlocksPrice() {
        Season season = SeasonAPI.getNowSeason();

        List<String> blocks = season.getSeasonBlocks();

        List<Integer> blockPrices = new ArrayList<>();

        for (String block : blocks) {
            String[] blockData = block.split(":");
            blockPrices.add(Integer.parseInt(blockData[1]));
        }
        return blockPrices;
    }
}