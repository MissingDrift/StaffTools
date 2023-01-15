package me.missingdrift.stafftools.listeners.chatmanaging;

import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntiCaps implements Listener {
    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        User u = new User(e.getPlayer());
        if (!StaffTools.getInstance().getConfig().getString("anticaps-status").equalsIgnoreCase("enabled"))
            return;
        int max = StaffTools.getInstance().getConfig().getInt("max-caps-characters");
        int total = 0;
        byte b;
        int i;
        char[] arrayOfChar;
        for (i = (arrayOfChar = e.getMessage().toCharArray()).length, b = 0; b < i; ) {
            char c = arrayOfChar[b];
            if (Character.isUpperCase(c))
                total++;
            b++;
        }
        if (total > max && !u.hasPerm("stafftools.anticaps.bypass") &&
                StaffTools.getInstance().getConfig().getString("chat_lock") == null) {
            e.setMessage(e.getMessage().toLowerCase());
            u.msg(Messages.anti_caps_message);
            return;
        }
        String m = e.getMessage();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (m.toLowerCase().contains("@" + p.getName().toLowerCase()) && StaffTools.getInstance().getConfig().getString("mentions-status").equalsIgnoreCase("enabled")) {
                User staff = new User(p);
                if (u.hasPerm("stafftools.mention") && !staff.isVanished() && staff.isTakingMentions()) {
                    e.setMessage(Util.c(m.toLowerCase().replace("@" + p.getName().toLowerCase(), String.valueOf(StaffTools.getInstance().getConfig().getString("mention").replace("%name%", staff.getName())) + ChatColor.RESET)));
                    Sound sound = null;
                    try {
                        sound = Sound.valueOf("ORB_PICKUP");
                    } catch (IllegalArgumentException ex) {
                        sound = Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP");
                    }
                    staff.msg(StaffTools.getInstance().getConfig().getString("mentioned-notification").replace("%name%", e.getPlayer().getName()));
                    p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
                }
            }
        }
        if (u.isInAuthentication()) {
            e.setCancelled(true);
            e.getRecipients().remove(e.getPlayer());
        }
    }
}
