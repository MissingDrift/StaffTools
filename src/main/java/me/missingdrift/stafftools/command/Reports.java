package me.missingdrift.stafftools.command;

import java.util.ArrayList;
import java.util.List;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reports implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.reports")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 1) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <page>"));
            return true;
        }
        int page = 0;
        try {
            page = Integer.parseInt(args[0]) - 1;
        } catch (NumberFormatException exc) {
            u.msg(Messages.argument_isnt_number.replace("%prefix%", Messages.prefix).replace("%arg%", args[0]));
            return true;
        }
        if (page + 1 < 1)
            return true;
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            User p = new User(player);
            if (p.getReportAmount(p.getUUID()) > 0)
                for (int i = 0; i < p.getReportAmount(p.getUUID()); i++) {
                    String log = StaffTools.getDataFile().getStringList("reportlog." + p.getUUID()).get(i);
                    list.add("&e" + p.getName() + "&8 | &6[" + "%counter%" + "] %n%" +
                            log);
                }
        }
        int pA = StaffTools.getInstance().getConfig().getInt("reports-per-page");
        int a = page * pA;
        int count = 0;
        if (a > list.size() || list.size() < 1) {
            u.msg(Messages.reports_page_doesnt_exist);
            return true;
        }
        u.msg(ChatColor.translateAlternateColorCodes('&', StaffTools.getInstance().getConfig().getString("report-page-spacer")));
        while (a + count < page * pA + pA && list.size() > a + count) {
            u.msg("&f - " + ((String)list.get(a + count)).replace("%n%", "\n")
                    .replace("%counter%", "Report: " + Integer.toString(a + count + 1) + "/" + list.size()));
            count++;
        }
        u.msg(ChatColor.translateAlternateColorCodes('&', StaffTools.getInstance().getConfig().getString("report-page-spacer")));
        return true;
    }
}
