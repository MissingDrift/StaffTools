package me.missingdrift.stafftools.command;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.vanish")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 0 && args.length != 1) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <name>"));
            return true;
        }
        if (args.length == 0) {
            u.toggleVanish();
            u.msg(Messages.toggled_vanish.replace("%prefix%", Messages.prefix).replace("%toggled%", u.isVanished() ? "enabled" : "disabled"));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                u.msg(Messages.target_is_offline.replace("%prefix%", Messages.prefix).replace("%name%", args[0]));
                return true;
            }
            User targetuser = new User(target);
            targetuser.toggleVanish();
            targetuser.msg(Messages.toggled_vanish.replace("%prefix%", Messages.prefix).replace("%toggled%", u.isVanished() ? "enabled" : "disabled"));
            u.msg(Messages.toggled_players_vanish.replace("%prefix%", Messages.prefix).replace("%name%", targetuser.getName()).replace("%toggled%", targetuser.isVanished() ? "enabled" : "disabled"));
        }
        return true;
    }
}
