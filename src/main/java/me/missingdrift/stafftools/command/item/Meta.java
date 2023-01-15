package me.missingdrift.stafftools.command.item;

import java.util.ArrayList;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Meta implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.item.modify")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 1 && args.length < 2) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <setlore/setname/reset> <...>"));
            return true;
        }
        ItemStack item = u.getPlayer().getItemInHand();
        if (item.getType() == null || item.getType() == Material.AIR) {
            u.msg(Messages.must_be_holding_item.replace("%prefix%", Messages.prefix));
            return true;
        }
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("setlore") && args.length >= 2) {
                String parse = "";
                for (int i = 1; i < args.length; i++)
                    parse = String.valueOf(parse) + " " + args[i];
                parse = parse.trim();
                if (item.hasItemMeta() && item.getItemMeta().hasLore())
                    for (String line : item.getItemMeta().getLore())
                        lore.add(Util.c(line));
                lore.add(Util.c(parse));
                meta.setLore(lore);
                item.setItemMeta(meta);
                u.msg(Messages.lore_changed.replace("%prefix%", Messages.prefix));
            }
            if (args[0].equalsIgnoreCase("setname") && args.length >= 2) {
                String name = "";
                for (int i = 1; i < args.length; i++)
                    name = String.valueOf(name) + " " + args[i];
                name = name.trim();
                meta.setDisplayName(Util.c(name));
                item.setItemMeta(meta);
                u.msg(Messages.name_changed.replace("%prefix%", Messages.prefix));
            }
        } else if (args[0].equalsIgnoreCase("reset") && args.length == 1) {
            meta.setDisplayName(null);
            meta.setLore(new ArrayList());
            for (ItemFlag flag : meta.getItemFlags()) {
                meta.removeItemFlags(new ItemFlag[] { flag });
            }
            item.setItemMeta(meta);
            u.msg(Messages.item_meta_reset.replace("%prefix%", Messages.prefix));
        }
        return true;
    }
}
