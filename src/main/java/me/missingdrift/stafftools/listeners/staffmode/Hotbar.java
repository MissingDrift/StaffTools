package me.missingdrift.stafftools.listeners.staffmode;

import java.util.ArrayList;
import java.util.Random;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Inventory;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.gui.Miner;
import me.missingdrift.stafftools.utility.gui.StaffList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Hotbar implements Listener {
    private static Random r = new Random();

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        User u = new User(e.getPlayer());
        ItemStack i = e.getPlayer().getItemInHand();
        if ((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) || !u.isInStaffmode() || !u.hasPerm("stafftools.staffmode") || i == null || i.getItemMeta() == null || i.getItemMeta().getDisplayName() == null || !i.hasItemMeta())
            return;
        if (i.getItemMeta().getDisplayName().contains("Vanish"))
            u.toggleVanish();
        if (i.getItemMeta().getDisplayName().matches(Inventory.getRandomTP().getItemMeta().getDisplayName())) {
            ArrayList<Player> list = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("stafftools.staffmode") && !p.getUniqueId().equals(u.getUUID()))
                    list.add(p);
            }
            if (list.size() < 1) {
                u.msg(Messages.teleport_random_fail.replace("%prefix%", Messages.prefix));
                return;
            }
            u.getPlayer().teleport((Entity)list.get(r.nextInt(list.size())));
            u.msg(Messages.teleport_random_successful.replace("%prefix%", Messages.prefix).replace("%name%", ((Player)list.get(r.nextInt(list.size()))).getName()));
            list.clear();
        }
        if (i.getItemMeta().getDisplayName().matches(Inventory.getMinerList().getItemMeta().getDisplayName()))
            Miner.gui(u);
        if (i.getItemMeta().getDisplayName().matches(Inventory.getStaffOnline().getItemMeta().getDisplayName()))
            StaffList.gui(u);
    }

    @EventHandler
    public void entityInteract(PlayerInteractAtEntityEvent e) {
        User u = new User(e.getPlayer());
        if (e.getRightClicked() instanceof Player) {
            User t = new User((Player)e.getRightClicked());
            ItemStack i = u.getPlayer().getItemInHand();
            if (!u.isInStaffmode() || !u.hasPerm("stafftools.staffmode") || i == null || i.getItemMeta() == null || i.getItemMeta().getDisplayName() == null || !i.hasItemMeta())
                return;
            if (i.getItemMeta().getDisplayName().matches(Inventory.getFollower().getItemMeta().getDisplayName())) {
                e.getRightClicked().setPassenger((Entity)e.getPlayer());
                u.msg(Messages.follower_message.replace("%prefix%", Messages.prefix).replace("%name%", e.getRightClicked().getName()));
            }
            if (i.getItemMeta().getDisplayName().matches(Inventory.getFreeze().getItemMeta().getDisplayName()))
                u.freeze(t);
            if (i.getItemMeta().getDisplayName().matches(Inventory.getInspectBook().getItemMeta().getDisplayName()))
                u.inspect(t, Messages.player_inspect_gui_name.replace("%name%", t.getName()));
        }
    }
}
