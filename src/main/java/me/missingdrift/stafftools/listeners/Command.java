package me.missingdrift.stafftools.listeners;

import java.util.List;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Command implements Listener {
    @EventHandler
    public void cmdExecute(PlayerCommandPreprocessEvent e) {
        User u = new User(e.getPlayer());
        for (Player p : Bukkit.getOnlinePlayers()) {
            User newUser = new User(p);
            if (!newUser.getUUID().equals(u.getUUID()) && newUser.isUsingCommandSpy() && newUser.hasPerm("stafftools.commandspy"))
                newUser.msg(Messages.commandspy_format.replace("%prefix%", Messages.prefix)
                        .replace("%name%", u.getName()).replace("%command%", e.getMessage()));
        }
        if (StaffTools.getInstance().getConfig().getString("bad-command-blocker-status").equalsIgnoreCase("enabled")) {
            if (u.getPlayer().isOp())
                return;
            List<String> blocked = StaffTools.getDataFile().getStringList("bad-command-list");
            boolean invalid = false;
            for (String bc : blocked) {
                if (e.getMessage().replace("/", "").equalsIgnoreCase(bc) && !e.getMessage().startsWith("/commandblocker") && !e.getMessage().startsWith("/cb")) {
                    e.setCancelled(true);
                    invalid = true;
                }
            }
            if (invalid)
                u.msg(Messages.bad_command_message);
        }
    }
}
