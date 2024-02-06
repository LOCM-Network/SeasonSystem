package me.phuongaz.season.form;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import me.onebone.economyapi.EconomyAPI;
import me.phuongaz.season.api.SeasonAPI;
import me.phuongaz.season.season.Season;
import me.phuongaz.season.utils.SeasonUtils;
import me.phuongaz.season.utils.Utils;
import ru.contentforge.formconstructor.form.CustomForm;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.Input;
import ru.contentforge.formconstructor.form.element.Toggle;

public class ShopForm {

    public static void mainForm(Player player){
        SimpleForm form = new SimpleForm(TextFormat.colorize("&l&0Cửa hàng"));
        form.addButton(TextFormat.colorize("&l&f● &2Mua vật phẩm &l&f●"), (p, btn) -> {
            sendShopForm(p);
        });
        form.addButton(TextFormat.colorize("&l&f● &2Thông tin mùa &l&f●"), (p, btn) -> {
            sendSeasonBlocksForm(p);
        });
        form.send(player);
    }
    
    public static void sendShopForm(Player player){
        Season season = SeasonAPI.getNowSeason();
        SimpleForm form = new SimpleForm(TextFormat.colorize("&l&0Cửa hàng&f (&2" + season.getName() + "&f)"));
        List<String> shops = Utils.getCurrentShop();
        for(String i : shops){
            String[] parts = i.split(":");
            int price = Integer.parseInt(parts[2]);
            Item item = Item.get(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            String button = TextFormat.colorize("&l&f● &2" + item.getName() + " &f●\n&2Giá&6 " + price + "&f(&0Xu&f)");
            form.addButton(button, (p, btn) -> {
                confirmForm(p, item, price);
            });
        }
        form.send(player);
    }

    public static void sendSeasonBlocksForm(Player player) {
        Season season = SeasonAPI.getNowSeason();
        String status = season.getStatus();
        List<String> blocksName = SeasonUtils.getSeasonBlocksName();
        List<Integer> price = SeasonUtils.getSeasonBlocksPrice();

        CustomForm form = new CustomForm(TextFormat.colorize("&l&0Mùa &2" + season.getName()));

        form.addElement(TextFormat.colorize("&l&fCác nông sản có thể phát triển vào " + status + " &l&f" + season.getName() + "&f:"));

        for (int i = 0; i < blocksName.size(); i++) {
            form.addElement(TextFormat.colorize("&l&f" + blocksName.get(i) + " &f có thể lên đến &e" + price.get(i) + " &fXu"));
        }
        form.send(player);
    }

    private static void confirmForm(Player player, Item item, int price){
        int itcount = Utils.getItemCount(player, item);
        CustomForm form = new CustomForm(TextFormat.colorize("&l&eCửa hàng mùa (" + SeasonAPI.getNowSeason().getName() + ")"));
        form.addElement(TextFormat.colorize("&l&fLoại vật phẩm:&e " + item.getName()));
        form.addElement(TextFormat.colorize("&l&fGiá thu mua vật phẩm:&e " + price + "&f/&e1"));
        form.addElement(TextFormat.colorize("&l&fBạn đang có: &e" + itcount));
        form.addElement("amount", new Input(TextFormat.colorize("&l&eNhập số lượng")));
        form.addElement("confirm", new Toggle(TextFormat.colorize("&l&aXác nhận bán:")));
        form.setNoneHandler((p) -> sendShopForm(player));
        form.setHandler((p, response) -> {
            boolean confirm = response.getToggle("confirm").getValue();
            String count = response.getInput("amount").getValue();
            if(confirm){
                try{
                    int lastamount = itcount - Integer.parseInt(count);
                    Item cloneItem = item.clone();
                    cloneItem.setCount(lastamount);
                    if(lastamount >= 0){
                        p.getInventory().remove(item);
                        p.getInventory().addItem(cloneItem);
                        EconomyAPI.getInstance().addMoney(p, Integer.parseInt(count) * price);
                        noteForm(player, "&l&fBán thành công vật phẩm nhận được &e" + Integer.parseInt(count) * price + "&f xu");
                        return;
                    }
                    noteForm(player, "&l&cNhập sai số lượng, số lượng không đủ");
                }catch(NumberFormatException exception){
                    noteForm(player, "&l&cNhập sai số lượng, số lượng phải là số");
                }
            }
        });
        form.send(player);
    }

    private static void noteForm(Player player, String content){
        SimpleForm form = new SimpleForm(TextFormat.colorize("&l&0NOTICE"));
        form.setContent(TextFormat.colorize(content));
        form.addButton(TextFormat.colorize("&l&2Quay lại"), (b, button) -> sendShopForm(player));
        form.send(player);
    }
}
