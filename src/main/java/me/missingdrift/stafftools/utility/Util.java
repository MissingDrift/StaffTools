package me.missingdrift.stafftools.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Util {
    public static String c(String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }

    public static String version() {
        String version = Bukkit.getVersion();
        int l = 0;
        char[] charArray;
        for (int length = (charArray = version.toCharArray()).length, k = 0; k < length; k++) {
            Character c = Character.valueOf(charArray[k]);
            l++;
            if (c.equals(Character.valueOf('(')))
                break;
        }
        return version.substring(l).replace("MC: ", "").replace(")", "").toString();
    }

    public static void log(String log) {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(log);
    }

    public static void msg(Player p, String msg) {
        p.sendMessage(c(msg));
    }

    public static void toggleChat(User u) {
        boolean toggled = !(StaffTools.getDataFile().getString("chat_lock") == null);
        if (toggled) {
            StaffTools.getDataFile().set("chat_lock", null);
            Bukkit.broadcastMessage(Messages.chat_lock_disabled.replace("%name%", u.getName()));
        } else {
            StaffTools.getDataFile().set("chat_lock", "true");
            Bukkit.broadcastMessage(Messages.chat_lock_enabled.replace("%name%", u.getName()));
        }
    }

    public static void slowChat(User u, int seconds, boolean disable) {
        if (disable) {
            StaffTools.getDataFile().set("chat_slow", null);
            Bukkit.broadcastMessage(Messages.chat_slow_disabled.replace("%name%", u.getName()));
        } else {
            StaffTools.getDataFile().set("chat_slow", Integer.valueOf(seconds));
            Bukkit.broadcastMessage(Messages.chat_slowed.replace("%name%", u.getName()).replace("%seconds%", Integer.toString(seconds)));
        }
    }

    public static void clearlag() {
        int total = 0;
        for (World w : Bukkit.getWorlds()) {
            for (Entity entity : w.getEntities()) {
                if (entity instanceof org.bukkit.entity.Item) {
                    total++;
                    entity.remove();
                }
            }
        }
        Bukkit.broadcastMessage(Messages.clear_lag_message.replace("%total%", Integer.toString(total)));
    }

    public static ItemStack itembuilder(String name, String[] lore, Material mat, int amount) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(c(name));
        ArrayList<String> lore1 = new ArrayList<>();
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = lore).length, b = 0; b < i; ) {
            String s = arrayOfString[b];
            lore1.add(c(s));
            b++;
        }
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DESTROYS });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_PLACED_ON });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        meta.setLore(lore1);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getSkull(Player p, boolean y) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
        meta.setOwner(p.getName());
        meta.setDisplayName(c(p.getDisplayName()));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(c("&7IGN: &f" + p.getName()));
        if (y)
            lore.add(c("&7Y: &f" + p.getLocation().getBlockY()));
        lore.add(c(""));
        lore.add(c("&7&lClick to teleport"));
        meta.setLore(lore);
        skull.setItemMeta((ItemMeta)meta);
        return skull;
    }

    public static void staffMessage(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("stafftools.staff"))
                p.sendMessage(c(message));
        }
    }

    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        Date date = new Date();
        return format.format(date);
    }

    public static ChatColor getColor(String colorname) {
        ChatColor color = ChatColor.WHITE;
        String str;
        switch ((str = colorname).hashCode()) {
            case -1852623997:
                if (!str.equals("dark_blue"))
                    break;
                color = ChatColor.DARK_BLUE;
                break;
            case -1852469876:
                if (!str.equals("dark_gray"))
                    break;
                color = ChatColor.DARK_GRAY;
                break;
            case -1591987974:
                if (!str.equals("dark_green"))
                    break;
                color = ChatColor.DARK_GREEN;
                break;
            case -1026963764:
                if (!str.equals("underline"))
                    break;
                color = ChatColor.UNDERLINE;
                break;
            case -1008851410:
                if (!str.equals("orange"))
                    break;
                color = ChatColor.GOLD;
                break;
            case -976943172:
                if (!str.equals("purple"))
                    break;
                color = ChatColor.DARK_PURPLE;
                break;
            case -972521773:
                if (!str.equals("strikethrough"))
                    break;
                color = ChatColor.STRIKETHROUGH;
                break;
            case -734239628:
                if (!str.equals("yellow"))
                    break;
                color = ChatColor.YELLOW;
                break;
            case 112785:
                if (!str.equals("red"))
                    break;
                color = ChatColor.RED;
                break;
            case 3002044:
                if (!str.equals("aqua"))
                    break;
                color = ChatColor.AQUA;
                break;
            case 3027034:
                if (!str.equals("blue"))
                    break;
                color = ChatColor.BLUE;
                break;
            case 3029637:
                if (!str.equals("bold"))
                    break;
                color = ChatColor.BOLD;
                break;
            case 3068707:
                if (!str.equals("cyan"))
                    break;
                color = ChatColor.DARK_AQUA;
                break;
            case 3181155:
                if (!str.equals("gray"))
                    break;
                color = ChatColor.GRAY;
                break;
            case 3441014:
                if (!str.equals("pink"))
                    break;
                color = ChatColor.LIGHT_PURPLE;
                break;
            case 93818879:
                if (!str.equals("black"))
                    break;
                color = ChatColor.BLACK;
                break;
            case 98619139:
                if (!str.equals("green"))
                    break;
                color = ChatColor.GREEN;
                break;
            case 1741368392:
                if (!str.equals("dark_red"))
                    break;
                color = ChatColor.DARK_RED;
                break;
        }
        return color;
    }
}
