package me.missingdrift.stafftools.command;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Alerts implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.alerts")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 2) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <reports/xray/mentions/tickets> <name>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            u.msg(Messages.target_is_offline.replace("%prefix%", Messages.prefix).replace("%name%", args[1]));
            return true;
        }
        User t = new User(target);
        if (args[0].equalsIgnoreCase("reports"))
            t.toggleReceivingReports();
        if (args[0].equalsIgnoreCase("xray")) {
            t.toggleXRayAlerts();
            if (t.getUUID().equals(u.getUUID())) {
                u.msg(Messages.xray_alerts_toggled.replace("%prefix%", Messages.prefix).replace("%toggle%", t.receivesXRayAlerts() ? "enabled" : "disabled"));
            } else {
                u.msg(Messages.xray_alerts_toggled.replace("%prefix%", Messages.prefix).replace("%toggle%", t.receivesXRayAlerts() ? "enabled" : "disabled"));
                u.msg(Messages.xray_alerts_toggled_player.replace("%name%", t.getName()).replace("%prefix%", Messages.prefix).replace("%toggle%", t.receivesXRayAlerts() ? "enabled" : "disabled"));
            }
        }
        if (args[0].equalsIgnoreCase("mentions"))
            u.toggleMentionAlerts();
        if (args[0].equalsIgnoreCase("tickets"))
            u.toggleTicketAlerts();
        return true;
    }
}
