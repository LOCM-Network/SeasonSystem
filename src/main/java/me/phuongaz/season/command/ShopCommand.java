package me.phuongaz.season.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import me.phuongaz.season.form.ShopForm;

public class ShopCommand extends Command{
    
    public ShopCommand(){
        super("seasonshop", "Season Shop", "/sshop", new String[]{"sshop", "season"});
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args){
        if(sender instanceof Player) ShopForm.mainForm((Player) sender);
        return true;
    }
}
