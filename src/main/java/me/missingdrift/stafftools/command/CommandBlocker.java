package me.missingdrift.stafftools.command;

import java.util.List;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBlocker implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.commandblocker.manage")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 2 && args.length != 1) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <add/remove/list> <command>"));
            return true;
        }
        List<String> list = StaffTools.getDataFile().getStringList("bad-command-list");
        String parse = "";
        if (args[0].equalsIgnoreCase("list") && args.length == 1) {
            for (String command : list)
                parse = parse.matches("") ? (String.valueOf(parse) + command) : (String.valueOf(parse) + "&e,&6 " + command);
            u.msg(Messages.commandblocker_listed_commands.replace("%prefix%", Messages.prefix).replace("%list%", parse));
        }
        if (args[0].equalsIgnoreCase("add") && args.length == 2) {
            list.add(args[1].replace("/", ""));
            StaffTools.getDataFile().set("bad-command-list", list);
            u.msg(Messages.commandblocker_added_command.replace("%prefix%", Messages.prefix).replace("%command%", args[1].replace("/", "")));
        }
        if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
            List<String> updated = StaffTools.getDataFile().getStringList("bad-command-list");
            for (String command : StaffTools.getDataFile().getStringList("bad-command-list")) {
                if (command.equalsIgnoreCase(args[1].replace("/", "")))
                    updated.remove(command);
            }
            StaffTools.getDataFile().set("bad-command-list", updated);
            u.msg(Messages.commandblocker_removed_command.replace("%prefix%", Messages.prefix).replace("%command%", args[1].replace("/", "")));
        }
        return true;
    }
}
