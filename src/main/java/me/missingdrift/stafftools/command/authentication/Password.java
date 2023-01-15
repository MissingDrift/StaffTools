package me.missingdrift.stafftools.command.authentication;

import java.util.UUID;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Password implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Util.msg((Player)sender, Messages.must_be_console);
            return true;
        }
        if (args.length != 3 && args.length != 2) {
            Util.log(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <name> <change/check> <password>"));
            return true;
        }
        UUID id = null;
        String name = "";
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            id = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
            name = Bukkit.getOfflinePlayer(args[0]).getName();
        } else {
            id = t.getUniqueId();
            name = t.getName();
        }
        if (args.length == 3 && args[1].equalsIgnoreCase("change") && StaffTools.getDataFile().getString("auth." + id) != null) {
            StaffTools.getDataFile().set("auth." + id, args[2].toLowerCase());
            Util.log(Messages.changed_password.replace("%name%", name).replace("%password%", args[2].toLowerCase()).replace("%prefix%", Messages.prefix));
        }
        if (args.length == 2 && args[1].equalsIgnoreCase("check") && StaffTools.getDataFile().getString("auth." + id) != null)
            Util.log(Messages.found_password.replace("%name%", name).replace("%password%", StaffTools.getDataFile().getString("auth." + id)).replace("%prefix%", Messages.prefix));
        return true;
    }
}
