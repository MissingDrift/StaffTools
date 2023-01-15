package me.missingdrift.stafftools.command.item;

import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Enchant implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.item.enchant")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 2 && args.length != 1) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <list/enchantment/clear> <level>"));
            return true;
        }
        ItemStack item = u.getPlayer().getItemInHand();
        if (item.getType() == null || item.getType() == Material.AIR) {
            u.msg(Messages.must_be_holding_item.replace("%prefix%", Messages.prefix));
            return true;
        }
        if (args[0].equalsIgnoreCase("list") && args.length == 1) {
            u.msg(Messages.enchantment_list.replace("%prefix%", Messages.prefix));
            return true;
        }
        if (args[0].equalsIgnoreCase("clear") && args.length == 1) {
            for (Enchantment ench : item.getEnchantments().keySet())
                item.removeEnchantment(ench);
            u.msg(Messages.enchantments_cleared.replace("%prefix%", Messages.prefix));
            return true;
        }
        if (getEnchantment(args[0]) == null) {
            u.msg(Messages.enchantment_not_found.replace("%prefix%", Messages.prefix).replace("%enchantment%", args[0]));
            return true;
        }
        int level = 1;
        try {
            level = Integer.parseInt(args[1]);
            item.addUnsafeEnchantment(getEnchantment(args[0]), level);
            u.msg(Messages.enchanted_item.replace("%prefix%", Messages.prefix).replace("%level%", Integer.toString(level)).replace("%enchantment%", getEnchantment(args[0]).getName().toUpperCase().replace("_", " ")));
        } catch (NumberFormatException exc) {
            u.msg(Messages.argument_isnt_number.replace("%prefix%", Messages.prefix).replace("%arg%", args[1]));
            return true;
        }
        return true;
    }

    private static Enchantment getEnchantment(String name) {
        name = name.toLowerCase();
        Enchantment result = null;
        String str;
        switch ((str = name).hashCode()) {
            case -1964679349:
                if (!str.equals("aqua_affinity"))
                    break;
                result = Enchantment.WATER_WORKER;
                break;
            case -1684858151:
                if (!str.equals("protection"))
                    break;
                result = Enchantment.PROTECTION_ENVIRONMENTAL;
                break;
            case -1571105471:
                if (!str.equals("sharpness"))
                    break;
                result = Enchantment.DAMAGE_ALL;
                break;
            case -874519716:
                if (!str.equals("thorns"))
                    break;
                result = Enchantment.THORNS;
                break;
            case -720514431:
                if (!str.equals("fire_aspect"))
                    break;
                result = Enchantment.FIRE_ASPECT;
                break;
            case -677216191:
                if (!str.equals("fortune"))
                    break;
                result = Enchantment.LOOT_BONUS_BLOCKS;
                break;
            case -161290517:
                if (!str.equals("feather_falling"))
                    break;
                result = Enchantment.PROTECTION_FALL;
                break;
            case 97513267:
                if (!str.equals("flame"))
                    break;
                result = Enchantment.ARROW_FIRE;
                break;
            case 106858757:
                if (!str.equals("power"))
                    break;
                result = Enchantment.ARROW_DAMAGE;
                break;
            case 107028782:
                if (!str.equals("punch"))
                    break;
                result = Enchantment.ARROW_KNOCKBACK;
                break;
            case 173173288:
                if (!str.equals("infinity"))
                    break;
                result = Enchantment.ARROW_INFINITE;
                break;
            case 350056506:
                if (!str.equals("looting"))
                    break;
                result = Enchantment.LOOT_BONUS_MOBS;
                break;
            case 620514517:
                if (!str.equals("silk_touch"))
                    break;
                result = Enchantment.SILK_TOUCH;
                break;
            case 686066415:
                if (!str.equals("projectile_protection"))
                    break;
                result = Enchantment.PROTECTION_PROJECTILE;
                break;
            case 961218153:
                if (!str.equals("efficiency"))
                    break;
                result = Enchantment.DIG_SPEED;
                break;
            case 976288699:
                if (!str.equals("knockback"))
                    break;
                result = Enchantment.KNOCKBACK;
                break;
            case 1430090624:
                if (!str.equals("blast_protection"))
                    break;
                result = Enchantment.PROTECTION_EXPLOSIONS;
                break;
            case 1603571740:
                if (!str.equals("unbreaking"))
                    break;
                result = Enchantment.DURABILITY;
                break;
            case 1640856381:
                if (!str.equals("depth_strider"))
                    break;
                result = Enchantment.DEPTH_STRIDER;
                break;
        }
        return result;
    }
}
