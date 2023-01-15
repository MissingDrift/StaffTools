package me.missingdrift.stafftools.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WL {
    private static FileConfiguration data = StaffTools.getDataFile();

    public boolean isWhitelisted() {
        return (data.getString("whitelist") != null && data.getString("whitelist").equalsIgnoreCase("enabled"));
    }

    public String toggleWhitelist(User u) {
        if (isWhitelisted())
            data.set("whitelisted", null);
        data.set("whitelist", isWhitelisted() ? "disabled" : "enabled");
        List<String> list = new ArrayList<>();
        list.add(u.getUUID().toString());
        data.set("whitelisted", isWhitelisted() ? list : null);
        if (isWhitelisted() && StaffTools.getInstance().getConfig().getString("kick-on-whitelist").equalsIgnoreCase("enabled"))
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.isOp() && !data.getStringList("whitelisted").contains(p.getName()) && data.getString("whitelisted") != null)
                    p.kickPlayer(Messages.cant_join_not_whitelisted_message);
            }
        return Util.c(Messages.whitelist_toggled.replace("%prefix%", Messages.prefix).replace("%toggled%", isWhitelisted() ? "enabled" : "disabled"));
    }

    public void disableWL() {
        data.set("whitelisted", null);
        data.set("whitelist", "disabled");
    }

    public String addToWhitelist(UUID id, String name) {
        if (isWhitelisted()) {
            List<String> list = null;
            if (data.getString("whitelisted") == null) {
                list = new ArrayList<>();
            } else {
                list = data.getStringList("whitelisted");
            }
            if (list.contains(id.toString()))
                return Messages.player_already_whitelisted.replace("%prefix%", Messages.prefix).replace("%name%", name);
            list.add(id.toString());
            data.set("whitelisted", list);
        } else {
            return Util.c(Messages.whitelist_required.replace("%prefix%", Messages.prefix));
        }
        return Util.c(Messages.whitelisted_player.replace("%prefix%", Messages.prefix).replace("%name%", name));
    }

    public List<String> getWhitelistedPlayers() {
        List<String> list = null;
        if (isWhitelisted()) {
            list = data.getStringList("whitelisted");
            List<String> newList = new ArrayList<>();
            String playerName = "";
            for (String uuid : list) {
                Player target = Bukkit.getPlayer(UUID.fromString(uuid));
                if (target == null) {
                    playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                } else {
                    playerName = target.getName();
                }
                newList.add(playerName);
            }
            return newList;
        }
        list = new ArrayList<>();
        list.add(Messages.whitelist_required.replace("%prefix%", Messages.prefix));
        return list;
    }

    public String removeFromWhitelist(UUID id, String name) {
        if (isWhitelisted()) {
            List<String> list = data.getStringList("whitelisted");
            if (!list.contains(id.toString()))
                return Messages.player_isnt_whitelisted.replace("%prefix%", Messages.prefix).replace("%name%", name);
            list.remove(id.toString());
            data.set("whitelisted", list);
        } else {
            return Messages.whitelist_required.replace("%prefix%", Messages.prefix);
        }
        return Messages.unwhitelisted_player.replace("%prefix%", Messages.prefix).replace("%name%", name);
    }
}
