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
import ru.contentforge.formconstructor.form.ModalForm;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.Input;
import ru.contentforge.formconstructor.form.element.Toggle;

public class ShopForm {
    
    public static void sendShopForm(Player player){
        Season season = SeasonAPI.getNowSeason();
        SimpleForm form = new SimpleForm(TextFormat.colorize("SEASON SHOP (" + season.getName() + ")"));
        List<String> shops = Utils.getCurrentShop();
        for(String i : shops){
            String[] parts = i.split(":");
            int price = Integer.parseInt(parts[2]);
            Item item = Item.get(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            String button = TextFormat.colorize(" " + item.getName() + " \nGiá " + price + "/1");
            form.addButton(button, (p, btn) -> {
                confirmForm(p, item, price);
            });
        }
        form.send(player);
    }

    private static void confirmForm(Player player, Item item, int price){
        CustomForm form = new CustomForm(TextFormat.colorize("&l&eCửa hàng mùa (" + SeasonAPI.getNowSeason().getName() + ")"));
        form.addElement(TextFormat.colorize("Loại vật phẩm: " + item.getName()));
        form.addElement(TextFormat.colorize("&l&eGía thu mua: " + price + "/1"));
        form.addElement("count", new Input(TextFormat.colorize("&l&eNhập số lượng")));
        form.addElement("confirm", new Toggle(TextFormat.colorize("Xac nhan ban")));
        form.setNoneHandler((p) -> sendShopForm(player));
        form.setHandler((p, response) -> {
            Boolean confirm = response.getToggle("confirm").getValue();
            String count = response.getInput("count").getValue();
            if(confirm){
                if(Integer.parseInt(count) % 1 == 0){
                    item.setCount(Integer.parseInt(count));
                    if(p.getInventory().contains(item)){
                        p.getInventory().remove(item);
                        EconomyAPI.getInstance().addMoney(p, Integer.parseInt(count) * price);
                        noteForm(player, "&l&fBán thành công vật phẩm nhận đưuọc &e" + Integer.parseInt(count) * price + "&f xu");
                        return;
                    }
                }
                noteForm(player, "&l&cNhập sai số lượng");
            }
        });
        form.send(player);
    }

    private static void noteForm(Player player, String content){
        ModalForm form = new ModalForm(TextFormat.colorize("&l&0NOTICE"));
        form.setContent(TextFormat.colorize(content));
        form.setHandler((p, response) -> sendShopForm(player));
        form.send(player);
    }
}
