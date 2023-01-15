package me.missingdrift.stafftools.antiswear;

import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AntiSwear implements CommandExecutor {
    private me.missingdrift.stafftools.utility.Messages Messages;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.antiswear.manage")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 2 && args.length != 1) {
            u.msg(Messages.usage.replace("%prefix%",
                    Messages.prefix).replace("%usage%", "/" + label + " <add/remove/words> <word>"));
            return true;
        }
        List<String> stringList = StaffTools.getDataFile().getStringList("swear-words");
        if (args.length == 1 && args[0].equalsIgnoreCase("words")) {
            String list = "";
            for (String word : stringList)
                list = list.matches("") ? (String.valueOf(list) + word) : (String.valueOf(list) + ", " + word);
            u.msg(Messages.swear_word_list.replace("%prefix%", Messages.prefix).replace("%words%", list));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
            stringList.add(args[1]);
            StaffTools.getDataFile().set("swear-words", stringList);
            u.msg(Messages.swear_word_added.replace("%prefix%", Messages.prefix).replace("%word%", args[1]));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            for (String word : stringList) {
                if (word.equalsIgnoreCase(args[1])) {
                    stringList.remove(word);
                    break;
                }
            }
            StaffTools.getDataFile().set("swear-words", stringList);
            u.msg(Messages.swear_word_removed.replace("%prefix%", Messages.prefix).replace("%word%", args[1]));
        }
        return true;
    }
}
