package me.missingdrift.stafftools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import me.missingdrift.stafftools.antiswear.AntiSwear;
import me.missingdrift.stafftools.antiswear.SwearListener;
import me.missingdrift.stafftools.command.Alerts;
import me.missingdrift.stafftools.command.ChatColor;
import me.missingdrift.stafftools.command.ClearLag;
import me.missingdrift.stafftools.command.CommandBlocker;
import me.missingdrift.stafftools.command.CommandSpy;
import me.missingdrift.stafftools.command.Freeze;
import me.missingdrift.stafftools.command.God;
import me.missingdrift.stafftools.command.Inspect;
import me.missingdrift.stafftools.command.Log;
import me.missingdrift.stafftools.command.Notes;
import me.missingdrift.stafftools.command.Ping;
import me.missingdrift.stafftools.command.PlayTime;
import me.missingdrift.stafftools.command.Primary;
import me.missingdrift.stafftools.command.Report;
import me.missingdrift.stafftools.command.Reports;
import me.missingdrift.stafftools.command.Rollback;
import me.missingdrift.stafftools.command.Speed;
import me.missingdrift.stafftools.command.StaffChat;
import me.missingdrift.stafftools.command.StaffMode;
import me.missingdrift.stafftools.command.Strip;
import me.missingdrift.stafftools.command.Tickets;
import me.missingdrift.stafftools.command.Vanish;
import me.missingdrift.stafftools.command.Whitelist;
import me.missingdrift.stafftools.command.authentication.Login;
import me.missingdrift.stafftools.command.authentication.Password;
import me.missingdrift.stafftools.command.authentication.Register;
import me.missingdrift.stafftools.command.chatmanaging.Chat;
import me.missingdrift.stafftools.command.item.Enchant;
import me.missingdrift.stafftools.command.item.Fix;
import me.missingdrift.stafftools.command.item.Meta;
import me.missingdrift.stafftools.listeners.ChatListener;
import me.missingdrift.stafftools.listeners.Command;
import me.missingdrift.stafftools.listeners.Restriction;
import me.missingdrift.stafftools.listeners.XRay;
import me.missingdrift.stafftools.listeners.chatmanaging.AntiCaps;
import me.missingdrift.stafftools.listeners.staffmode.Hotbar;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import me.missingdrift.stafftools.utility.WL;
import me.missingdrift.stafftools.utility.gui.Miner;
import me.missingdrift.stafftools.utility.gui.StaffList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffTools extends JavaPlugin {
    static StaffTools instance;

    private WL wl;

    public static File[] file = new File[] { new File("plugins/StaffTools/data/"), new File("plugins/StaffTools/data/data.yml") };

    static FileConfiguration data = (FileConfiguration)YamlConfiguration.loadConfiguration(file[1]);

    public void onEnable() {
        if (!file[0].exists())
            file[0].mkdir();
        if (!file[1].exists())
            try {
                file[1].createNewFile();
            } catch (IOException e) {
                Util.log("Something went wrong whilst creating the bans file.");
                e.printStackTrace();
            }
        this.wl = new WL();
        saveDefaultConfig();
        instance = this;
        init();
        if (getConfig().getString("automatic-clear-lag").equalsIgnoreCase("enabled"))
            ClearLag.start(getConfig().getInt("clear-lag-repeat-interval-seconds"));
        if (this.wl.isWhitelisted() && !isEnabled(".whitelist"))
            this.wl.disableWL();
        String version = Util.version();
        boolean warning = true;
        String[] supported = { "1.12", "1.11", "1.10", "1.9", "1.8", "1.8.8", "1.12.2", "1.13", "1.13.1", "1.13.2" };
        for (int j = 0; j < supported.length; j++) {
            if (version.toLowerCase().contains(supported[j])) {
                warning = false;
                break;
            }
        }
    }

    public void onDisable() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            User u = new User(online);
            if (u.isInStaffmode())
                u.toggleStaffMode();
        }
        try {
            data.save(file[1]);
        } catch (IOException e) {
            e.printStackTrace();
            Util.log("Failed to save the data file: " + file[1].getPath());
        }
        data = null;
        file = null;
        this.wl = null;
        instance = null;
        byte b;
        int i;
        Map[] arrayOfMap;
        for (i = (arrayOfMap = User.mapArray).length, b = 0; b < i; ) {
            Map<?, ?> map = arrayOfMap[b];
            map.clear();
            b++;
        }
        List[] arrayOfList;
        for (i = (arrayOfList = User.listArray).length, b = 0; b < i; ) {
            List<?> list = arrayOfList[b];
            list.clear();
            b++;
        }
    }

    public static StaffTools getInstance() {
        return instance;
    }

    public static FileConfiguration getDataFile() {
        return data;
    }

    Listener[] array = new Listener[] { (Listener)new SwearListener(), (Listener)new XRay(), (Listener)new AntiCaps(), (Listener)new ChatListener(), (Listener)new Command(), (Listener)new Restriction(), (Listener)new Hotbar(), (Listener)new Miner(), (Listener)new StaffList() };

    void init() {
        byte b;
        int i;
        Listener[] arrayOfListener;
        for (i = (arrayOfListener = this.array).length, b = 0; b < i; ) {
            Listener l = arrayOfListener[b];
            Bukkit.getPluginManager().registerEvents(l, (Plugin)this);
            b++;
        }
        if (isEnabled(".staffchat"))
            getCommand("staffchat").setExecutor((CommandExecutor)new StaffChat());
        if (isEnabled(".report"))
            getCommand("report").setExecutor((CommandExecutor)new Report());
        if (isEnabled(".chat"))
            getCommand("chat").setExecutor((CommandExecutor)new Chat());
        if (isEnabled(".commandspy"))
            getCommand("commandspy").setExecutor((CommandExecutor)new CommandSpy());
        if (isEnabled(".staffmode"))
            getCommand("staffmode").setExecutor((CommandExecutor)new StaffMode());
        if (isEnabled(".freeze"))
            getCommand("freeze").setExecutor((CommandExecutor)new Freeze());
        if (isEnabled(".vanish"))
            getCommand("vanish").setExecutor((CommandExecutor)new Vanish());
        if (isEnabled(".chatcolor"))
            getCommand("chatcolor").setExecutor((CommandExecutor)new ChatColor());
        if (isEnabled(".clearlag"))
            getCommand("clearlag").setExecutor((CommandExecutor)new ClearLag());
        if (isEnabled(".notes"))
            getCommand("notes").setExecutor((CommandExecutor)new Notes());
        if (isEnabled(".stafftools"))
            getCommand("stafftools").setExecutor((CommandExecutor)new Primary());
        if (isEnabled(".item"))
            getCommand("item").setExecutor((CommandExecutor)new Meta());
        if (isEnabled(".enchant"))
            getCommand("enchant").setExecutor((CommandExecutor)new Enchant());
        if (isEnabled(".alerts"))
            getCommand("alerts").setExecutor((CommandExecutor)new Alerts());
        if (isEnabled(".antiswear"))
            getCommand("antiswear").setExecutor((CommandExecutor)new AntiSwear());
        if (isEnabled(".commandblocker"))
            getCommand("commandblocker").setExecutor((CommandExecutor)new CommandBlocker());
        if (isEnabled(".register"))
            getCommand("register").setExecutor((CommandExecutor)new Register());
        if (isEnabled(".login"))
            getCommand("login").setExecutor((CommandExecutor)new Login());
        if (isEnabled(".password"))
            getCommand("password").setExecutor((CommandExecutor)new Password());
        if (isEnabled(".ticket"))
            getCommand("ticket").setExecutor((CommandExecutor)new Tickets());
        if (isEnabled(".whitelist"))
            getCommand("whitelist").setExecutor((CommandExecutor)new Whitelist());
        if (isEnabled(".rollback"))
            getCommand("rollback").setExecutor((CommandExecutor)new Rollback());
        if (isEnabled(".reports"))
            getCommand("reports").setExecutor((CommandExecutor)new Reports());
        if (isEnabled(".playtime"))
            getCommand("playtime").setExecutor((CommandExecutor)new PlayTime());
        if (isEnabled(".logs"))
            getCommand("logs").setExecutor((CommandExecutor)new Log());
        if (isEnabled(".strip"))
            getCommand("strip").setExecutor((CommandExecutor)new Strip());
        if (isEnabled(".inspect"))
            getCommand("inspect").setExecutor((CommandExecutor)new Inspect());
        if (isEnabled(".fix"))
            getCommand("fix").setExecutor((CommandExecutor)new Fix());
        if (isEnabled(".speed"))
            getCommand("speed").setExecutor((CommandExecutor)new Speed());
        if (isEnabled(".ping"))
            getCommand("ping").setExecutor((CommandExecutor)new Ping());
        if (isEnabled(".god"))
            getCommand("god").setExecutor((CommandExecutor)new God());
    }

    boolean isEnabled(String command) {
        return getConfig().getString("commands" + command).equalsIgnoreCase("enabled");
    }
}
