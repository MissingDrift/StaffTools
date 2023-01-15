package me.missingdrift.stafftools.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.StaffTools;
import org.bukkit.configuration.file.FileConfiguration;

public class Logs {
    private FileConfiguration d = StaffTools.getDataFile();

    public void clearLogs(UUID id) {
        if (getLogs(id).size() > 0)
            this.d.set("logs." + id, null);
    }

    public String addCommand(String command) {
        List<String> newList = getCommands();
        if (newList.contains(command))
            return "";
        newList.add(command.toLowerCase());
        this.d.set("logcommands", newList);
        return "";
    }

    public void removeCommand(String command) {
        List<String> newList = getCommands();
        if (newList.contains(command.toLowerCase())) {
            newList.remove(command.toLowerCase());
            this.d.set("logcommands", newList);
        }
    }

    public void addLog(UUID id, String log) {
        List<String> logs = getLogs(id);
        logs.add(log);
        this.d.set("logs." + id, logs);
    }

    public List<String> getCommands() {
        return (this.d.getString("logcommands") == null) ? new ArrayList<>() : this.d.getStringList("logcommands");
    }

    public List<String> getLogs(UUID id) {
        return (this.d.getString("logs." + id) == null) ? new ArrayList<>() : this.d.getStringList("logs." + id);
    }
}
