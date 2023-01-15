package me.missingdrift.stafftools.command;

import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Primary implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.primary")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 1) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <help/reload>"));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            StaffTools.getInstance().reloadConfig();
            u.msg(Messages.plugin_reloaded_message.replace("%prefix%", Messages.prefix));
        }
        if (args[0].equalsIgnoreCase("help")) {
            u.msg("");
            u.msg("                &6&lSTAFFTOOLS HELP");
            u.msg("                     &7(v 1.0)");
            u.msg("");
            u.msg("              &eAuthor: &6MissingDrift");
            u.msg("");
            u.msg("");
        }
        return true;
    }
}
