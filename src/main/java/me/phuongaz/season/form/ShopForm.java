package me.phuongaz.season.form;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import me.onebone.economyapi.EconomyAPI;
import me.phuongaz.season.api.SeasonAPI;
import me.phuongaz.season.season.Season;
import me.phuongaz.season.utils.Utils;
import ru.contentforge.formconstructor.form.CustomForm;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.Input;
import ru.contentforge.formconstructor.form.element.Toggle;

public class ShopForm {
    
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
            Boolean confirm = response.getToggle("confirm").getValue();
            String count = response.getInput("amount").getValue();
            System.out.println(count);
            if(confirm){
                try{
                    int lastamount = itcount - Integer.parseInt(count);
                    item.setCount(lastamount);
                    if(lastamount >= 0){
                        p.getInventory().remove(item);
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
