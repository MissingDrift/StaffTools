package me.missingdrift.stafftools.command;

import java.util.UUID;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Logs;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Log implements CommandExecutor {
    protected Logs logManager = new Logs();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && (
                args.length == 0 || (args.length != 2 && !args[0].equalsIgnoreCase("list")) || (args[0].equalsIgnoreCase("list") && args.length != 1))) {
            Util.log(Messages.usage.replace("%prefix%", Messages.prefix).replace("%usage%", "/" + label + " <clear/remove>" +
                    " <name/command>"));
            return true;
        }
        UUID id = null;
        String name = "";
        if (!(sender instanceof Player) &&
                !args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove") &&
                !args[0].equalsIgnoreCase("list")) {
            Player t = Bukkit.getPlayer(args[1]);
            if (t == null) {
                id = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                name = Bukkit.getOfflinePlayer(args[1]).getName();
            } else {
                id = t.getUniqueId();
                name = t.getName();
            }
        }
        if (!(sender instanceof Player)) {
            if (args[0].equalsIgnoreCase("remove")) {
                if (!this.logManager.getCommands().contains(args[1])) {
                    Util.log(Messages.logcommand_doesnt_exist.replace("%command%", args[1]));
                    return true;
                }
                this.logManager.removeCommand(args[1]);
                Util.log(Messages.logcommand_removed.replace("%prefix%", Messages.prefix).replace("%command%", args[1]));
            }
            if (args[0].equalsIgnoreCase("clear")) {
                if (this.logManager.getLogs(id).size() < 1) {
                    Util.log(Messages.no_logs_found.replace("%prefix%", Messages.prefix).replace("%name%", name));
                    return true;
                }
                this.logManager.clearLogs(id);
                Util.log(Messages.logs_cleared.replace("%prefix%", Messages.prefix).replace("%name%", name));
                return true;
            }
        } else {
            User u = new User((Player)sender);
            if (!u.hasPerm("stafftools.logs")) {
                u.msg(Messages.noperm);
                return true;
            }
            if (args.length == 0 || (args.length != 2 && !args[0].equalsIgnoreCase("list")) || (args[0].equalsIgnoreCase("list") && args.length != 1)) {
                u.msg(Messages.usage.replace("%prefix%",
                        Messages.prefix).replace("%usage%", "/" + label + " <check/clear/add/remove/list>" +
                        " <name/command>"));
                return true;
            }
            String n1 = "";
            UUID uuid = null;
            if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove") &&
                    !args[0].equalsIgnoreCase("list")) {
                Player t = Bukkit.getPlayer(args[1]);
                if (t == null) {
                    uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                    n1 = Bukkit.getOfflinePlayer(args[1]).getName();
                } else {
                    uuid = t.getUniqueId();
                    n1 = t.getName();
                }
            }
            if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("remove")) {
                u.msg(Messages.must_be_console);
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                String s = "";
                for (String c : this.logManager.getCommands())
                    s = s.equals("") ? (String.valueOf(s) + c) : (String.valueOf(s) + ", " + c);
                u.msg(Messages.logcommand_list.replace("%prefix%", Messages.prefix).replace("%list%", s));
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (this.logManager.getCommands().contains(args[1])) {
                    u.msg(Messages.logcommand_exists.replace("%command%", args[1]));
                    return true;
                }
                this.logManager.addCommand(args[1]);
                u.msg(Messages.logcommand_added.replace("%prefix%", Messages.prefix).replace("%command%", args[1]));
            }
            if (args[0].equalsIgnoreCase("check")) {
                if (this.logManager.getLogs(uuid).size() < 1) {
                    u.msg(Messages.no_logs_found.replace("%prefix%", Messages.prefix).replace("%name%", n1));
                    return true;
                }
                for (String log : this.logManager.getLogs(uuid))
                    u.msg(Util.c(log));
            }
        }
        return true;
    }
}
