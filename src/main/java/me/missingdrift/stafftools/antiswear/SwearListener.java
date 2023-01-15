package me.missingdrift.stafftools.antiswear;

import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class SwearListener implements Listener {
    @EventHandler
    public void speak(AsyncPlayerChatEvent e) {
        User u = new User(e.getPlayer());
        String msg = e.getMessage().replace(".", "").replace(",", "").replace("-", "").toLowerCase();
        List<String> swearList = StaffTools.getDataFile().getStringList("swear-words");
        if (StaffTools.getInstance().getConfig().getString("anti-swear-status").equalsIgnoreCase("enabled") && !u.hasPerm("stafftools.antiswear.bypass")) {
            byte b;
            int i;
            String[] arrayOfString;
            for (i = (arrayOfString = msg.split(" ")).length, b = 0; b < i; ) {
                String word = arrayOfString[b];
                if (swearList.contains(word)) {
                    e.setCancelled(true);
                    u.msg(Messages.swearing_message);
                    return;
                }
                b++;
            }
        }
    }
}
