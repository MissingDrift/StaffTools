package me.missingdrift.stafftools.command.item;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Fix implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.fix")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 0) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label));
            return true;
        }
        ItemStack item = u.getPlayer().getItemInHand();
        if (item.getDurability() > -1) {
            item.setDurability((short)-5000);
            u.msg(Messages.item_fixed_msg.replace("%prefix%", Messages.prefix));
        } else {
            u.msg(Messages.must_be_holding_item.replace("%prefix%", Messages.prefix));
            return true;
        }
        return true;
    }
}
