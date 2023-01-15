package me.missingdrift.stafftools.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.missingdrift.stafftools.StaffTools;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Note {
    private static FileConfiguration data = StaffTools.getDataFile();

    public static boolean hasNotes(UUID id) {
        return !(data.getString("notes." + id) == null);
    }

    public static void addNote(Player p, UUID id, String name, String note) {
        List<String> noteList = null;
        if (!hasNotes(id)) {
            noteList = new ArrayList<>();
            noteList.add("[" + p.getName() + "] " + note);
            data.set("notes." + id, noteList);
        } else {
            noteList = data.getStringList("notes." + id);
            noteList.add("[" + p.getName() + "] " + note);
            data.set("notes." + id, noteList);
        }
        Util.msg(p, Messages.added_note.replace("%prefix%", Messages.prefix).replace("%note%", note).replace("%name%", name));
    }

    public static String clearNotes(Player p, UUID id, String name) {
        if (!hasNotes(id))
            return Messages.doesnt_have_notes.replace("%name%", name).replace("%prefix%", Messages.prefix);
        data.set("notes." + id, null);
        return Messages.cleared_notes.replace("%name%", name).replace("%prefix%", Messages.prefix);
    }

    public static List<String> checkNotes(Player p, UUID id, String name) {
        List<String> notes = null;
        if (!hasNotes(id)) {
            notes = new ArrayList<>();
            notes.add(Messages.doesnt_have_notes.replace("%name%", name).replace("%prefix%", Messages.prefix));
            return notes;
        }
        notes = data.getStringList("notes." + id);
        return notes;
    }
}
