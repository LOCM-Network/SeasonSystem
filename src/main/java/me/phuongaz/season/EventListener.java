package me.phuongaz.season;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockCrops;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockGrowEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import cn.nukkit.utils.TextFormat;
import me.phuongaz.season.api.SeasonAPI;
import me.phuongaz.season.season.Season;
import me.phuongaz.season.utils.Utils;

public class EventListener implements Listener{

    private List<String> players = new ArrayList<>();
    
    @EventHandler
    public void onJoin(DataPacketReceiveEvent event){
        if(event.getPacket() instanceof SetLocalPlayerAsInitializedPacket){
            Player player = event.getPlayer();
            if(!this.players.contains(player.getName())){
                Season season = SeasonAPI.getNowSeason();
                int percen = 0;
                String ss = "";
                String day = Utils.getDay();
                switch (day) {
                    case "Monday":
                        percen = 5;
                        ss = "&aĐầu";
                    case "Tuesday":
                        percen = 25;
                        ss = "&aĐầu";
                        break;
                    case "Wednesday":
                        percen = 35;
                        ss = "&eGiữa";
                        break;
                    case "Thursday":
                        percen = 50;
                        ss = "&eGiữa";
                        break;
                    case "Friday":
                        percen = 60;
                        ss = "&eGiữa";
                        break;
                    case "Saturday":
                        percen = 85;
                        ss = "&cCuối";
                        break;
                    case "Sunday":
                        percen = 99;
                        ss = "&cCuối";
                        break;
                }
                this.players.add(player.getName());
                player.createBossBar(TextFormat.colorize("&l&e" + season.getName() + " &f(" + ss + "&f)"), percen);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        this.players.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onGrow(BlockGrowEvent event) {
        Block block = event.getBlock();
        if(!block.getLevel().getName().contains("skyblock")) {
            return;
        }
        if(block instanceof BlockCrops) {
            Season season = SeasonAPI.getNowSeason();
            int blockId = block.getId();
            if(!season.isBlockInSeason(blockId, 3)) {
                Random random = new Random();
                if(random.nextInt(100) < 40) {
                    event.setCancelled();
                }
            }
        }
    }
}
