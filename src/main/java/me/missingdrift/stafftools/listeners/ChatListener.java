package me.missingdrift.stafftools.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    public static Map<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        User u = new User(e.getPlayer());
        if (u.isFrozen())
            e.setFormat(String.valueOf(Messages.frozen_prefix) + " " + ChatColor.RESET + e.getFormat());
        if (u.usingStaffChat() && u.hasPerm("stafftools.staffchat")) {
            e.setCancelled(true);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("stafftools.staffchat"))
                    Util.msg(player, StaffTools.getInstance().getConfig().getString("staffchat-format")
                            .replace("%name%", u.getName()).replace("%message%", e.getMessage()));
            }
        } else {
            boolean toggled = !(StaffTools.getDataFile().getString("chat_lock") == null);
            if (toggled && !u.hasPerm("stafftools.lockchat.bypass")) {
                e.setCancelled(true);
                u.msg(Messages.chat_locked_speak);
            } else if (!toggled) {
                if (StaffTools.getDataFile().getString("chat_slow") != null && !u.getPlayer().hasPermission("stafftools.slowchat.bypass")) {
                    int timer = StaffTools.getDataFile().getInt("chat_slow");
                    if (cooldown.containsKey(u.getUUID())) {
                        long rem = ((Long)cooldown.get(u.getUUID())).longValue() / 1000L + timer - System.currentTimeMillis() / 1000L;
                        if (rem > 0L) {
                            e.setCancelled(true);
                            u.msg(Messages.on_cooldown_message.replace("%prefix%", Messages.prefix).replace("%time%", Long.toString(rem)));
                            return;
                        }
                    }
                    cooldown.put(u.getUUID(), Long.valueOf(System.currentTimeMillis()));
                }
                if (u.hasChatColor())
                    e.setMessage(u.getChatColor() + e.getMessage());
            }
        }
    }
}
