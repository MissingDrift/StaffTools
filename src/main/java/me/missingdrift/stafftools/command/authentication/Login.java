package me.missingdrift.stafftools.command.authentication;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Login implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.authenticate")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 1) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <password>"));
            return true;
        }
        if (u.isInAuthentication() && u.isRegistered())
            if (!args[0].toLowerCase().matches(u.getPassword(u.getUUID()))) {
                u.msg(Messages.login_fail);
            } else {
                u.removeFromAuthentication();
                u.msg(Messages.login_success);
            }
        return true;
    }
}
