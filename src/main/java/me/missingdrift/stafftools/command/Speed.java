package me.missingdrift.stafftools.command;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Speed implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.speed")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 2) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <fly/walk> <speed/reset>"));
            return true;
        }
        if (args[1].equalsIgnoreCase("reset")) {
            if (args[0].equalsIgnoreCase("fly") || args[0].equalsIgnoreCase("flight")) {
                u.getPlayer().setFlySpeed(0.2F);
                u.msg(Messages.fly_speed_reset.replace("%prefix%", Messages.prefix));
                return true;
            }
            if (args[0].equalsIgnoreCase("walk") || args[0].equalsIgnoreCase("walking")) {
                u.getPlayer().setWalkSpeed(0.2F);
                u.msg(Messages.walk_speed_reset.replace("%prefix%", Messages.prefix));
                return true;
            }
        }
        float f = 0.0F;
        try {
            f = Float.parseFloat(args[1]);
            if (f > 1.0F) {
                u.msg(Messages.number_too_big.replace("%prefix%", Messages.prefix).replace("%arg%", args[1]).replace("%max%", "&c(0.1-1)"));
                return true;
            }
        } catch (NumberFormatException nfe) {
            u.msg(Messages.argument_isnt_number.replace("%arg%", args[1]).replace("%prefix%", Messages.prefix));
            return true;
        }
        if (args[0].equalsIgnoreCase("fly") || args[0].equalsIgnoreCase("flight")) {
            u.getPlayer().setFlySpeed(f);
            u.msg(Messages.fly_speed_set.replace("%prefix%", Messages.prefix).replace("%speed%", Float.toString(f)));
        }
        if (args[0].equalsIgnoreCase("walk") || args[0].equalsIgnoreCase("walking")) {
            u.getPlayer().setWalkSpeed(f);
            u.msg(Messages.walk_speed_set.replace("%prefix%", Messages.prefix).replace("%speed%", Float.toString(f)));
        }
        return true;
    }
}
