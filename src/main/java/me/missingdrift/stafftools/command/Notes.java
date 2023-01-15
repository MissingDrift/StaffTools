package me.missingdrift.stafftools.command;

import java.util.UUID;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Note;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Notes implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.notes")) {
            u.msg(Messages.noperm);
            return true;
        }
        if ((args.length != 2 && args.length < 3) || (args.length < 3 && args[1].equalsIgnoreCase("add")) || (args.length != 2 && !args[1].equalsIgnoreCase("add"))) {
            u.msg(Messages.usage.replace("%prefix%", Messages.prefix).replace("%usage%",
                    "/" + label + " <name> <check/add/clear> <note>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        UUID id = null;
        String name = "";
        if (target == null) {
            OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
            id = target2.getUniqueId();
            name = target2.getName();
        } else {
            id = target.getUniqueId();
            name = target.getName();
        }
        if (args.length >= 3 && args[1].equalsIgnoreCase("add")) {
            String note = "";
            for (int i = 2; i < args.length; i++)
                note = String.valueOf(note) + " " + args[i];
            note = note.trim();
            Note.addNote(u.getPlayer(), id, name, note);
        }
        if (args.length == 2 && args[1].equalsIgnoreCase("check")) {
            if (!Note.hasNotes(id)) {
                u.msg(Messages.doesnt_have_notes.replace("%name%", name).replace("%prefix%", Messages.prefix));
                return true;
            }
            int count = 0;
            String noteId = "";
            for (String note : Note.checkNotes(u.getPlayer(), id, name)) {
                count++;
                noteId = "#" + count;
                u.msg(Messages.checknote_format.replace("%prefix%", Messages.prefix).replace("%note%", note).replace("%count%", noteId));
            }
        }
        if (args.length == 2 && args[1].equalsIgnoreCase("clear"))
            u.msg(Note.clearNotes(u.getPlayer(), id, name));
        return true;
    }
}
