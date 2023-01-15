package me.missingdrift.stafftools.command;

import java.util.UUID;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import me.missingdrift.stafftools.utility.WL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Whitelist implements CommandExecutor {
    private static WL whitelist = new WL();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.whitelist")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 1 && args.length != 2) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <toggle/list/add/remove> <name>"));
            return true;
        }
        if (args.length == 1)
            if (args[0].equalsIgnoreCase("toggle")) {
                u.msg(whitelist.toggleWhitelist(u));
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!whitelist.isWhitelisted()) {
                    u.msg(Messages.whitelist_required.replace("%prefix%", Messages.prefix));
                    return true;
                }
                String format = Messages.whitelisted_user_list;
                String list = "";
                for (String s : whitelist.getWhitelistedPlayers())
                    list = list.equals("") ? (String.valueOf(list) + s) : (String.valueOf(list) + ", " + s);
                list = list.trim();
                u.msg(format.replace("%count%",
                                Integer.toString(whitelist.getWhitelistedPlayers().size()))
                        .replace("%list%", list));
            }
        if (args.length == 2 && (
                args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove"))) {
            UUID id = null;
            String name = "";
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                id = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                name = Bukkit.getOfflinePlayer(args[1]).getName();
            } else {
                id = target.getUniqueId();
                name = target.getName();
            }
            if (args[0].equalsIgnoreCase("add"))
                u.msg(whitelist.addToWhitelist(id, name));
            if (args[0].equalsIgnoreCase("remove")) {
                if (whitelist.getWhitelistedPlayers().size() < 2) {
                    u.msg(Messages.cannot_unwhitelist_player.replace("%prefix%", Messages.prefix).replace("%name%", name));
                    return true;
                }
                u.msg(whitelist.removeFromWhitelist(id, name));
            }
        }
        return true;
    }
}
