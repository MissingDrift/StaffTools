package me.missingdrift.stafftools.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Logs;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Restriction implements Listener {
    private static HashMap<UUID, Long> cooldown = new HashMap<>();

    Logs logManager = new Logs();

    @EventHandler
    public void login(PlayerLoginEvent e) {
        User u = new User(e.getPlayer());
        if (!u.isWhitelisted(u.getUUID(), u.getName()) && !u.getPlayer().isOp())
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Messages.cant_join_not_whitelisted_message);
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player &&
                StaffTools.getDataFile().getString("godmode." + ((Player)e.getEntity()).getUniqueId()) != null)
            e.setCancelled(true);
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        User u = new User(e.getPlayer());
        if (u.isInStaffmode())
            u.toggleStaffMode();
        if (u.isFrozen())
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("stafftools.freeze"))
                    p.sendMessage(Messages.frozen_player_quit.replace("%prefix%", Messages.prefix).replace("%name%", u.getName()));
            }
        if (cooldown.containsKey(u.getUUID()))
            cooldown.remove(u.getUUID());
        if (ChatListener.cooldown.containsKey(u.getUUID()))
            ChatListener.cooldown.remove(u.getUUID());
        byte b;
        int i;
        List[] arrayOfList;
        for (i = (arrayOfList = User.listArray).length, b = 0; b < i; ) {
            List<?> list = arrayOfList[b];
            if (list.contains(u.getUUID()))
                list.remove(u.getUUID());
            b++;
        }
        Map[] arrayOfMap;
        for (i = (arrayOfMap = User.mapArray).length, b = 0; b < i; ) {
            Map<?, ?> maps = arrayOfMap[b];
            if (maps.containsKey(u.getUUID()))
                maps.remove(u.getUUID());
            b++;
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        User u = new User(e.getPlayer());
        if (u.isInStaffmode())
            u.toggleStaffMode();
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent e) {
        User u = new User(e.getPlayer());
        if (u.isInStaffmode() || u.isFrozen() || u.isVanished())
            e.setCancelled(true);
    }

    @EventHandler
    public void onTakeDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            User u = new User((Player)e.getEntity());
            if (u.isInStaffmode() || u.isFrozen() || u.isVanished())
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void commandPreprocess(PlayerCommandPreprocessEvent e) {
        User u = new User(e.getPlayer());
        if (u.isFrozen() && !e.getMessage().startsWith("/login") && !e.getMessage().startsWith("/register")) {
            e.setCancelled(true);
        } else {
            if (!u.hasPerm("stafftools.anticommandspam.bypass") && StaffTools.getInstance().getConfig().getString("anti-commandspam-status").equalsIgnoreCase("enabled")) {
                if (cooldown.containsKey(u.getUUID())) {
                    int timer = StaffTools.getInstance().getConfig().getInt("anti-commandspam-cooldown-seconds");
                    long rem = ((Long)cooldown.get(u.getUUID())).longValue() / 1000L + timer - System.currentTimeMillis() / 1000L;
                    if (rem > 0L) {
                        e.setCancelled(true);
                        u.msg(Messages.on_cooldown_message.replace("%prefix%", Messages.prefix).replace("%time%", Long.toString(rem)));
                        return;
                    }
                }
                cooldown.put(u.getUUID(), Long.valueOf(System.currentTimeMillis()));
            }
            String cmd = e.getMessage().split(" ")[0].replace("/", "").toLowerCase();
            if (this.logManager.getCommands().contains(cmd) &&
                    StaffTools.getInstance().getConfig().getString("logcommand-status").equalsIgnoreCase("enabled"))
                this.logManager.addLog(u.getUUID(), Messages.logcommand_format.replace("%date%", Util.getCurrentTime()).replace("%name%", u.getName()).replace("%command%", cmd));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        User u = new User(e.getPlayer());
        if (u.isInStaffmode() || u.isFrozen() || u.isVanished())
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPvP(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            User d = new User((Player)e.getDamager());
            if (d.isInStaffmode() || d.isFrozen() || d.isVanished())
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        User u = new User(e.getPlayer());
        if (u.isInStaffmode() || u.isFrozen() || u.isVanished())
            e.setCancelled(true);
    }

    @EventHandler
    public void onTrigger(PlayerInteractEvent e) {
        User u = new User(e.getPlayer());
        Block b = e.getClickedBlock();
        if (b == null)
            return;
        if (b != null && (b.getType() == Material.STONE_PLATE || b.getType() == Material.WOOD_PLATE) && (u.isInStaffmode() || u.isVanished() || u.isFrozen()))
            e.setCancelled(true);
        if (u.isInStaffmode() && (
                e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST)) {
            e.setCancelled(true);
            Chest chest = (Chest)e.getClickedBlock().getState();
            Inventory gui = Bukkit.createInventory((InventoryHolder)u.getPlayer(), 54, "Chest");
            byte b1;
            int i;
            ItemStack[] arrayOfItemStack;
            for (i = (arrayOfItemStack = chest.getInventory().getContents()).length, b1 = 0; b1 < i; ) {
                ItemStack item = arrayOfItemStack[b1];
                if (item != null)
                    gui.addItem(new ItemStack[] { item });
                b1++;
            }
            u.getPlayer().openInventory(gui);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        User u = new User((Player)e.getWhoClicked());
        if (u.isInStaffmode() || u.isFrozen() || e.getInventory().getTitle().equals("Inspecting"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        User u = new User(e.getPlayer());
        if (u.isInStaffmode() || u.isFrozen() || u.isVanished())
            e.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        User u = new User(e.getPlayer());
        if (u.isFrozen())
            e.setTo(e.getFrom());
    }

    @EventHandler
    public void onJoin1(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("stafftools.authenticate") && StaffTools.getInstance().getConfig().getString("staff-authentication-status").equalsIgnoreCase("enabled")) {
            User.frozen.add(p.getUniqueId());
            User.auth.add(p.getUniqueId());
            User s = new User(p);
            s.msg(s.isRegistered() ? Messages.must_login_msg : Messages.must_register_msg);
            return;
        }
        if (p.hasPermission("stafftools.vanished.see"))
            return;
        for (Player instaff : Bukkit.getOnlinePlayers()) {
            User staff = new User(instaff);
            if (staff.isVanished() && p.canSee(staff.getPlayer()))
                p.hidePlayer(instaff);
        }
    }

    @EventHandler
    public void die(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            User u = new User(e.getEntity());
            if (u.isInStaffmode())
                u.toggleStaffMode();
            u.saveInventory(u.getPlayer());
        }
    }
}
