package me.missingdrift.stafftools.command.chatmanaging;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.chat.manage")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 1 && args.length != 2) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <clear, lock, slow> <seconds/disable>"));
            return true;
        }
        if (args[0].equalsIgnoreCase("clear") && args.length == 1) {
            for (int i = 0; i < 100; i++)
                Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(Messages.chat_cleared.replace("%name%", u.getName()));
        }
        if (args[0].equalsIgnoreCase("lock") && args.length == 1)
            Util.toggleChat(u);
        if (args[0].equalsIgnoreCase("slow") && args.length == 2)
            if (args[1].equalsIgnoreCase("disable")) {
                Util.slowChat(u, 1, true);
            } else {
                try {
                    if (Integer.parseInt(args[1]) < 1) {
                        u.msg(Messages.number_too_low.replace("%prefix%", Messages.prefix).replace("%arg%", args[1]));
                        return true;
                    }
                    Util.slowChat(u, Integer.parseInt(args[1]), false);
                } catch (NumberFormatException exc) {
                    u.msg(Messages.argument_isnt_number.replace("%prefix%", Messages.prefix).replace("%arg%", args[1]));
                }
            }
        return true;
    }
}
