package me.missingdrift.stafftools.command;

import java.util.UUID;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Report implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.report")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length < 2) {
            if (!u.hasPerm("stafftools.report.clear") && !u.hasPerm("stafftools.report.check")) {
                u.msg(Messages.usage.replace("%prefix%",
                        Messages.prefix).replace("%usage%", "/" + label + " <name> <reason>"));
            } else {
                u.msg(Messages.usage.replace("%prefix%",
                        Messages.prefix).replace("%usage%", "/" + label + " <name> <clear/check/reason>"));
                return true;
            }
        } else {
            UUID id;
            String name;
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                id = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                name = Bukkit.getOfflinePlayer(args[0]).getName();
            } else {
                id = player.getUniqueId();
                name = player.getName();
            }
            if (args[1].equalsIgnoreCase("clear") && args.length == 2) {
                if (!u.hasPerm("stafftools.report.clear")) {
                    u.msg(Messages.noperm);
                    return true;
                }
                u.clearReports(id, name);
            }
            if (args[1].equalsIgnoreCase("check") && args.length == 2) {
                if (!u.hasPerm("stafftools.report.check")) {
                    u.msg(Messages.noperm);
                    return true;
                }
                u.msg(Messages.check_report_info.replace("%prefix%", Messages.prefix).replace("%name%", name).replace("%count%", Integer.toString(StaffTools.getDataFile().getInt("reports." + id))));
                for (String log : u.getReportLogs(id))
                    u.msg(Util.c(log));
            } else if (!args[1].equalsIgnoreCase("check") && !args[1].equalsIgnoreCase("clear")) {
                String reason = "";
                byte b;
                int i;
                String[] arrayOfString;
                for (i = (arrayOfString = args).length, b = 0; b < i; ) {
                    String argument = arrayOfString[b];
                    reason = reason.matches("") ? (String.valueOf(reason) + argument) : (String.valueOf(reason) + " " + argument);
                    b++;
                }
                u.report(id, name, reason.replace(args[0], "").trim());
            }
        }
        return true;
    }
}
