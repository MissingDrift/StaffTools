package me.missingdrift.stafftools.listeners;

import me.missingdrift.stafftools.objects.User;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class XRay implements Listener {
    @EventHandler
    public void xray(BlockBreakEvent e) {
        User u = new User(e.getPlayer());
        Block b = e.getBlock();
        if (!u.hasPerm("stafftools.xray.bypass")) {
            if (b.getType() != Material.DIAMOND_ORE &&
                    b.getType() != Material.GOLD_ORE && b.getType() != Material.MOB_SPAWNER)
                return;
            String ore = "";
            switch (b.getType()) {
                case MOB_SPAWNER:
                    ore = "Spawner";
                    break;
                case DIAMOND_ORE:
                    ore = "Diamond Ore";
                    break;
                case GOLD_ORE:
                    ore = "Gold Ore";
                    break;
            }
            u.flagXray(ore);
        }
    }
}
