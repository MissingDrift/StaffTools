package me.missingdrift.stafftools.utility.gui;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Inventory;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class StaffList implements Listener {
    public static void gui(User u) {
        Inventory i = (Inventory) Bukkit.createInventory((InventoryHolder)u.getPlayer(), 54, Messages.staff_list_gui_name);
        int[] filler = new int[]{
                0, 1, 2, 3, 4, 5, 6, 7, 8, 45,
                46, 47, 48, 49, 50, 51, 52, 53};
        for (int b = 0; b < filler.length; b++)
            i.setItem(filler[b], Inventory.getGlassPane((byte)15));
        for (Player p : Bukkit.getOnlinePlayers())
            if (p.hasPermission("stafftools.stafflist"))
                i.addItem(new ItemStack[]{Util.getSkull(p, false)});
        u.getPlayer().openInventory((org.bukkit.inventory.Inventory) i);
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase(Messages.staff_list_gui_name) &&
                e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getItemMeta().getLore() != null) {
            Player target = null;
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.getDisplayName().matches(e.getCurrentItem().getItemMeta().getDisplayName())) {
                    target = online;
                    break;
                }
            }
            if (target != null) {
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().teleport((Entity)target);
                Util.msg((Player)e.getWhoClicked(), Messages.teleport_random_successful.replace("%prefix%", Messages.prefix).replace("%name%", target.getName()));
            } else {
                Util.msg((Player)e.getWhoClicked(), Messages.target_is_offline.replace("%prefix%", Messages.prefix).replace("%name%", ""));
            }
        }
    }
}
