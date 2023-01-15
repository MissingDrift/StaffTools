package me.missingdrift.stafftools.utility;

import me.missingdrift.stafftools.StaffTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventory {
    static FileConfiguration cfg = StaffTools.getInstance().getConfig();

    private static String[] lore = new String[] { "" };

    public static ItemStack getGlassPane(byte parambyte) {
        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, parambyte);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        pane.setItemMeta(meta);
        return pane;
    }

    public static ItemStack getRandomTP() {
        return Util.itembuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("staffmode-randomtp-name")), lore,
                Material.getMaterial(cfg.getString("staffmode-randomtp-material")), cfg.getInt("staffmode-randomtp-amount"));
    }

    public static ItemStack getFreeze() {
        return Util.itembuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("staffmode-freeze-name")), lore,
                Material.getMaterial(cfg.getString("staffmode-freeze-material")), cfg.getInt("staffmode-freeze-amount"));
    }

    public static ItemStack getMinerList() {
        return Util.itembuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("staffmode-minerlist-name")), lore,
                Material.getMaterial(cfg.getString("staffmode-minerlist-material")), cfg.getInt("staffmode-minerlist-amount"));
    }

    public static ItemStack getStaffOnline() {
        return Util.itembuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("staffmode-stafflist-name")), lore,
                Material.getMaterial(cfg.getString("staffmode-stafflist-material")), cfg.getInt("staffmode-stafflist-amount"));
    }

    public static ItemStack getInspectBook() {
        return Util.itembuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("staffmode-inspectbook-name")), lore,
                Material.getMaterial(cfg.getString("staffmode-inspectbook-material")), cfg.getInt("staffmode-inspectbook-amount"));
    }

    public static ItemStack getFollower() {
        return Util.itembuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("staffmode-follower-name")), lore,
                Material.getMaterial(cfg.getString("staffmode-follower-material")), cfg.getInt("staffmode-follower-amount"));
    }

    public static ItemStack getVanishOn() {
        ItemStack von = new ItemStack(Material.INK_SACK, cfg.getInt("staffmode-vanishon-amount"), (short)10);
        ItemMeta vm = von.getItemMeta();
        vm.setDisplayName(Util.c(cfg.getString("staffmode-vanishon-name")));
        vm.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        von.setItemMeta(vm);
        return von;
    }

    public static ItemStack getVanishOff() {
        ItemStack von = new ItemStack(Material.INK_SACK, cfg.getInt("staffmode-vanishoff-amount"), (short)8);
        ItemMeta vm = von.getItemMeta();
        vm.setDisplayName(Util.c(cfg.getString("staffmode-vanishoff-name")));
        vm.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        von.setItemMeta(vm);
        return von;
    }

    public static void setInventoryLayout(Player p) {
        p.getInventory().setItem(cfg.getInt("staffmode-randomtp-slot"), getRandomTP());
        p.getInventory().setItem(cfg.getInt("staffmode-freeze-slot"), getFreeze());
        p.getInventory().setItem(cfg.getInt("staffmode-minerlist-slot"), getMinerList());
        p.getInventory().setItem(cfg.getInt("staffmode-stafflist-slot"), getStaffOnline());
        p.getInventory().setItem(cfg.getInt("staffmode-inspectbook-slot"), getInspectBook());
        p.getInventory().setItem(cfg.getInt("staffmode-vanishon-slot"), getVanishOn());
        p.getInventory().setItem(cfg.getInt("staffmode-follower-slot"), getFollower());
    }

    public void setItem(int i, ItemStack glassPane) {
    }

    public void addItem(ItemStack[] itemStacks) {
    }
}
