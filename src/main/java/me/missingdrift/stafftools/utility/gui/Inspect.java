package me.missingdrift.stafftools.utility.gui;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Inventory;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class Inspect {
    public static void inspectGUI(Player p, Player t, String title) {
        Inventory i = (Inventory) Bukkit.createInventory((InventoryHolder)p, 54, title);
        int[] filler = { 36, 37, 38, 39, 40, 41, 42, 43, 44 };
        if (t.getInventory().getContents() != null)
            ((org.bukkit.inventory.Inventory) i).setContents(t.getInventory().getContents());
        for (int b = 0; b < filler.length; b++)
            ((org.bukkit.inventory.Inventory) i).setItem(filler[b], Inventory.getGlassPane((byte)15));
        if (t.getInventory().getItemInHand() != null)
            ((org.bukkit.inventory.Inventory) i).setItem(45, t.getInventory().getItemInHand());
        if (t.getInventory().getHelmet() != null)
            ((org.bukkit.inventory.Inventory) i).setItem(46, t.getInventory().getHelmet());
        if (t.getInventory().getChestplate() != null)
            ((org.bukkit.inventory.Inventory) i).setItem(47, t.getInventory().getChestplate());
        if (t.getInventory().getLeggings() != null)
            ((org.bukkit.inventory.Inventory) i).setItem(48, t.getInventory().getLeggings());
        if (t.getInventory().getBoots() != null)
            ((org.bukkit.inventory.Inventory) i).setItem(49, t.getInventory().getBoots());
        User user = new User(t);
        ((org.bukkit.inventory.Inventory) i).setItem(53, Util.itembuilder("&eAdditional Info", new String[] { " ", "&fHealth:&7 " + t.getHealth(),
                "&fHunger:&7 " + t.getFoodLevel(), "&fFrozen:&7 " + (user.isFrozen() ? "Yes" : "No"), "&fFlying:&7 " + (t.getAllowFlight() ? "Yes" : "No"), "&fGamemode:&7 " + t.getGameMode().toString().toUpperCase() }, Material.PAPER, 1));
        p.openInventory((org.bukkit.inventory.Inventory) i);
    }
}