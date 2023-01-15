package me.missingdrift.stafftools.objects;

import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.listeners.ChatListener;
import me.missingdrift.stafftools.utility.Inventory;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import me.missingdrift.stafftools.utility.WL;
import me.missingdrift.stafftools.utility.gui.Inspect;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class User {
    private Player p;

    private User u;

    private static FileConfiguration data = StaffTools.getDataFile();

    private static WL whitelist = new WL();

    private static Map<UUID, Long> reportCD = new HashMap<>();

    private static Map<UUID, ItemStack[]> armor = (Map)new HashMap<>();

    private static Map<UUID, Integer> xray = new HashMap<>();

    private static Map<UUID, ItemStack[]> contents = (Map)new HashMap<>();

    private static List<UUID> staffmode = new ArrayList<>();

    private static List<UUID> vanish = new ArrayList<>();

    public static List<UUID> auth = new ArrayList<>();

    public static List<UUID> frozen = new ArrayList<>();

    public static List[] listArray = new List[] { auth, staffmode, vanish, frozen, Ticket.ticketList };

    public static Map[] mapArray = new Map[] { reportCD, armor, contents, xray, ChatListener.cooldown, Ticket.tickets, Ticket.cooldown };

    public User(Player p) {
        this.p = p;
        this.u = this;
    }

    public Player getPlayer() {
        return this.p;
    }

    public String getName() {
        return this.p.getName();
    }

    public UUID getUUID() {
        return this.p.getUniqueId();
    }

    public boolean usingStaffChat() {
        return !(data.getString("staffchat." + this.u.getUUID()) == null);
    }

    public String getPassword(UUID id) {
        return (data.getString("auth." + id) == null) ? null : data.getString("auth." + id);
    }

    public boolean isInAuthentication() {
        return auth.contains(this.u.getUUID());
    }

    public void removeFromAuthentication() {
        auth.remove(this.u.getUUID());
        frozen.remove(this.u.getUUID());
    }

    public boolean isReceivingTicketNotif() {
        return (data.getString("ticketnotif." + this.u.getUUID()) == null);
    }

    public boolean isWhitelisted(UUID id, String name) {
        if (!whitelist.isWhitelisted())
            return true;
        if (whitelist.isWhitelisted() && (
                whitelist.getWhitelistedPlayers().contains(name) || whitelist.getWhitelistedPlayers().contains(id.toString())))
            return true;
        return false;
    }

    public void toggleGodMode(User t) {
        if (data.getString("godmode." + t.getUUID()) == null) {
            data.set("godmode." + t.getUUID(), t.getUUID().toString());
        } else {
            data.set("godmode." + t.getUUID(), null);
        }
        if (this.u.getUUID().toString().equals(t.getUUID().toString())) {
            this.u.msg(Messages.toggled_godmode.replace("%toggled%", (data.getString("godmode." + t.getUUID()) == null) ? "disabled" : "enabled").replace("%prefix%", Messages.prefix));
        } else {
            t.msg(Messages.toggled_godmode.replace("%toggled%", (data.getString("godmode." + t.getUUID()) == null) ? "disabled" : "enabled").replace("%prefix%", Messages.prefix));
            this.u.msg(Messages.toggled_godmode_player.replace("%toggled%", (data.getString("godmode." + t.getUUID()) == null) ? "disabled" : "enabled").replace("%prefix%", Messages.prefix).replace("%name%", t.getName()));
        }
    }

    public String strip(Player p) {
        List<ItemStack> items = new ArrayList<>();
        byte b;
        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = p.getInventory().getArmorContents()).length, b = 0; b < i; ) {
            ItemStack item = arrayOfItemStack[b];
            items.add(new ItemStack(Material.AIR));
            if (p.getInventory().firstEmpty() == -1)
                return Messages.player_inventory_full.replace("%prefix%", Messages.prefix).replace("%name%", p.getName());
            items.remove(item);
            if (item != null)
                p.getInventory().addItem(new ItemStack[] { item });
            p.getInventory().setArmorContents(items.<ItemStack>toArray(new ItemStack[items.size()]));
            b++;
        }
        return Messages.player_stripped.replace("%prefix%", Messages.prefix).replace("%name%", p.getName());
    }

    public String getPlaytime() {
        Statistic tick = null;
        boolean minute = false;
        try {
            tick = Statistic.valueOf("PLAY_ONE_TICK");
        } catch (IllegalArgumentException iae) {
            tick = Statistic.valueOf("PLAY_ONE_MINUTE");
        }
        int time = this.u.getPlayer().getStatistic(tick) / (minute ? 1 : 20) / (minute ? 1 : 60);
        long tL = Long.parseLong(Integer.toString(time * 60 * 1000));
        return String.format("%02dd %02dh %02dm", new Object[] { Long.valueOf(TimeUnit.MILLISECONDS.toDays(tL)), Long.valueOf(tL / 3600000L % 24L),
                Long.valueOf(tL / 60000L % 60L) });
    }

    public void flagXray(String ore) {
        if (xray.containsKey(this.u.getUUID())) {
            xray.put(this.u.getUUID(), Integer.valueOf(((Integer)xray.get(this.u.getUUID())).intValue() + 1));
        } else {
            xray.put(this.u.getUUID(), Integer.valueOf(1));
        }
        for (Player staff : Bukkit.getOnlinePlayers()) {
            User s = new User(staff);
            if (s.receivesXRayAlerts() && s.hasPerm("stafftools.xray.notify"))
                s.msg(Messages.xray_alert_format.replace("%prefix%", Messages.prefix).replace("%name%", this.u.getName()).replace("%vl%", Integer.toString(!xray.containsKey(this.u.getUUID()) ? 0 : ((Integer)xray.get(this.u.getUUID())).intValue())).replace("%ore%", ore));
        }
    }

    public void toggleTicketAlerts() {
        if (isReceivingTicketNotif()) {
            data.set("ticketnotif." + this.u.getUUID(), this.u.getUUID().toString());
        } else {
            data.set("ticketnotif." + this.u.getUUID(), null);
        }
        this.u.msg(Messages.ticket_alerts_toggled.replace("%prefix%", Messages.prefix).replace("%toggled%", this.u.isReceivingTicketNotif() ? "enabled" : "disabled"));
    }

    public boolean hasPerm(String node) {
        return this.u.getPlayer().hasPermission(node);
    }

    public boolean isRegistered() {
        return !(data.getString("auth." + this.u.getUUID()) == null);
    }

    public void register(String password) {
        data.set("auth." + this.u.getUUID(), password.toLowerCase());
        removeFromAuthentication();
        this.u.msg(Messages.register_success);
    }

    public void msg(String msg) {
        this.u.getPlayer().sendMessage(Util.c(msg));
    }

    public boolean hasChatColor() {
        return !(data.getString("chatcolor." + this.u.getUUID()) == null);
    }

    public ChatColor getChatColor() {
        return findChatColor();
    }

    public void toggleMentionAlerts() {
        if (!this.u.isTakingMentions()) {
            StaffTools.getDataFile().set("mentions." + this.u.getUUID(), null);
        } else {
            StaffTools.getDataFile().set("mentions." + this.u.getUUID(), this.u.getUUID().toString());
        }
        this.u.msg(Messages.mention_alerts_toggled.replace("%toggle%", this.u.isTakingMentions() ? "enabled" : "disabled").replace("%prefix%", Messages.prefix));
    }

    public boolean isTakingMentions() {
        return (data.getString("mentions." + this.u.getUUID()) == null);
    }

    public void setChatColor(String colorName) {
        ChatColor desired = Util.getColor(colorName.toLowerCase());
        data.set("chatcolor." + this.u.getUUID(), colorName.toLowerCase());
        this.u.msg(Messages.chatcolor_changed.replace("%color%", desired.name().toUpperCase()).replace("%prefix%", Messages.prefix));
    }

    private ChatColor findChatColor() {
        ChatColor color = ChatColor.WHITE;
        if (!hasChatColor())
            return ChatColor.WHITE;
        color = Util.getColor(data.getString("chatcolor." + this.u.getUUID()));
        return color;
    }

    public boolean receivesXRayAlerts() {
        return (data.getString("xray." + this.u.getUUID()) == null);
    }

    public void toggleXRayAlerts() {
        if (!receivesXRayAlerts()) {
            data.set("xray." + this.u.getUUID(), null);
        } else {
            data.set("xray." + this.u.getUUID(), this.u.getUUID().toString());
        }
    }

    public boolean isInStaffmode() {
        return staffmode.contains(this.u.getUUID());
    }

    public boolean isFrozen() {
        return frozen.contains(this.u.getUUID());
    }

    public void saveInventory(Player p) {
        List<ItemStack> items = new ArrayList<>();
        List<ItemStack> armors = new ArrayList<>();
        for (int length = (p.getInventory().getArmorContents()).length, i = 0; i < length; i++) {
            ItemStack armor = p.getInventory().getArmorContents()[i];
            if (armor != null)
                armors.add(armor);
        }
        for (int length2 = (p.getInventory().getContents()).length, j = 0; j < length2; j++) {
            ItemStack content = p.getInventory().getContents()[j];
            if (content != null)
                items.add(content);
        }
        data.set("inventory." + p.getUniqueId() + ".contents", items);
        data.set("inventory." + p.getUniqueId() + ".armor", armors);
    }

    public String rollbackInventory(Player p) {
        if (data.getString("inventory." + p.getUniqueId()) == null) {
            this.u.msg(Messages.cant_find_inventory.replace("%prefix%", Messages.prefix).replace("%name%", p.getName()));
            return "";
        }
        ItemStack[] items = (ItemStack[])data.getList("inventory." + p.getUniqueId() + ".contents").toArray((Object[])new ItemStack[data.getList("inventory." + p.getUniqueId() + ".contents").size()]), armor = (ItemStack[])data.getList("inventory." + p.getUniqueId() + ".armor").toArray((Object[])new ItemStack[data.getList("inventory." + p.getUniqueId() + ".armor").size()]);
        p.getInventory().setArmorContents(armor);
        p.getInventory().setContents(items);
        data.set("inventory." + p.getUniqueId(), null);
        this.u.msg(Messages.inventory_rollbacked_staff.replace("%prefix%", Messages.prefix).replace("%name%", p.getName()));
        p.sendMessage(Util.c(Messages.inventory_rollbacked));
        return "";
    }

    public void freeze(User t) {
        if (!t.isFrozen()) {
            frozen.add(t.getUUID());
            t.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999999, 999999));
            t.msg(Messages.frozen_message);
            this.u.msg(Messages.frozen_player.replace("%prefix%", Messages.prefix).replace("%name%", t.getName()));
        } else {
            frozen.remove(t.getUUID());
            t.getPlayer().removePotionEffect(PotionEffectType.SLOW);
            t.msg(Messages.unfrozen_message);
            this.u.msg(Messages.unfroze_player.replace("%prefix%", Messages.prefix).replace("%name%", t.getName()));
        }
    }

    public boolean isVanished() {
        return vanish.contains(this.u.getUUID());
    }

    public void toggleVanish() {
        if (isVanished()) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!all.canSee(this.u.getPlayer()))
                    all.showPlayer(this.u.getPlayer());
            }
            vanish.remove(this.u.getUUID());
        } else {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!all.hasPermission("stafftools.vanished.see"))
                    all.hidePlayer(this.u.getPlayer());
            }
            vanish.add(this.u.getUUID());
        }
        if (this.u.isInStaffmode())
            if (isVanished()) {
                this.u.getPlayer().getInventory().setItem(StaffTools.getInstance().getConfig().getInt("staffmode-vanishon-slot"), Inventory.getVanishOn());
            } else {
                this.u.getPlayer().getInventory().setItem(StaffTools.getInstance().getConfig().getInt("staffmode-vanishoff-slot"), Inventory.getVanishOff());
            }
    }

    public void inspect(User t, String title) {
        Inspect.inspectGUI(this.u.getPlayer(), t.getPlayer(), title);
    }

    public void toggleStaffMode() {
        PlayerInventory i = this.u.getPlayer().getInventory();
        Player p = this.u.getPlayer();
        if (isInStaffmode()) {
            p.setGameMode(GameMode.SURVIVAL);
            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            if (isVanished())
                toggleVanish();
            staffmode.remove(this.u.getUUID());
            p.setFlySpeed(0.2F);
            p.setWalkSpeed(0.2F);
            i.clear();
            i.setArmorContents(null);
            i.setContents(contents.get(this.u.getUUID()));
            i.setArmorContents(armor.get(this.u.getUUID()));
            this.u.msg(Messages.staffmode_toggled.replace("%prefix%", Messages.prefix).replace("%status%", isInStaffmode() ? "enabled" : "disabled"));
        } else {
            armor.put(this.u.getUUID(), i.getArmorContents());
            contents.put(this.u.getUUID(), i.getContents());
            i.clear();
            i.setArmorContents(null);
            p.setGameMode(GameMode.CREATIVE);
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999999, 999999));
            p.setFlySpeed(0.3F);
            p.setWalkSpeed(0.3F);
            if (!isVanished())
                toggleVanish();
            staffmode.add(this.u.getUUID());
            Inventory.setInventoryLayout(this.u.getPlayer());
            this.u.msg(Messages.staffmode_toggled.replace("%prefix%", Messages.prefix).replace("%status%", isInStaffmode() ? "enabled" : "disabled"));
        }
    }

    public void toggleStaffChat(boolean toggle) {
        if (toggle) {
            data.set("staffchat." + this.u.getUUID(), this.u.getUUID().toString());
        } else {
            data.set("staffchat." + this.u.getUUID(), null);
        }
        this.u.msg(toggle ? Messages.staff_chat_enabled.replace("%prefix%", Messages.prefix) : Messages.staff_chat_disabled.replace("%prefix%", Messages.prefix));
    }

    public int getReportAmount(UUID id) {
        return (data.getString("reports." + id) == null) ? 0 : data.getInt("reports." + id);
    }

    public boolean isUsingCommandSpy() {
        return !(data.getString("commandspy." + this.u.getUUID()) == null);
    }

    public void toggleCommandSpy(boolean using) {
        if (using) {
            data.set("commandspy." + this.u.getUUID(), null);
        } else {
            data.set("commandspy." + this.u.getUUID(), this.u.getUUID().toString());
        }
        this.u.msg(Messages.commandspy_toggled.replace("%prefix%", Messages.prefix).replace("%toggle%", isUsingCommandSpy() ? "enabled" : "disabled"));
    }

    public boolean isReceivingReports() {
        return (data.getString("reportAlerts." + this.u.getUUID()) == null);
    }

    public void toggleReceivingReports() {
        if (isReceivingReports()) {
            data.set("reportAlerts." + this.u.getUUID(), this.u.getUUID().toString());
        } else {
            data.set("reportAlerts." + this.u.getUUID(), null);
        }
        this.u.msg(Messages.report_alerts_toggled.replace("%prefix%", Messages.prefix).replace("%toggled%", isReceivingReports() ? "enabled" : "disabled"));
    }

    public void report(UUID id, String name, String reason) {
        int timer = StaffTools.getInstance().getConfig().getInt("report-cooldown-seconds");
        if (reportCD.containsKey(this.u.getUUID())) {
            long rem = ((Long)reportCD.get(this.u.getUUID())).longValue() / 1000L + timer - System.currentTimeMillis() / 1000L;
            if (rem > 0L) {
                this.u.msg(Messages.on_cooldown_message.replace("%time%", Long.toString(rem)).replace("%prefix%", Messages.prefix));
                return;
            }
        }
        List<String> reports = (getReportAmount(id) > 0) ? data.getStringList("reportlog." + id) : new ArrayList<>();
        data.set("reports." + id, Integer.valueOf(getReportAmount(id) + 1));
        reports.add(Messages.reportlog_format.replace("%date%", Util.getCurrentTime()).replace("%reason%", reason).replace("%reporter%", this.u.getName()));
        data.set("reportlog." + id, reports);
        this.u.msg(Messages.player_reported.replace("%name%", name).replace("%prefix%", Messages.prefix).replace("%reason%", reason));
        for (Player p : Bukkit.getOnlinePlayers()) {
            User staff = new User(p);
            if (p.hasPermission("stafftools.report.notify") && staff.isReceivingReports())
                Util.msg(p, Messages.reported_notification.replace("%prefix%", Messages.prefix).replace("%target_name%", name).replace("%name%", this.u.getName()).replace("%reason%", reason));
        }
        reportCD.put(this.u.getUUID(), Long.valueOf(System.currentTimeMillis()));
    }

    public List<String> getReportLogs(UUID id) {
        return (getReportAmount(id) > 0) ? data.getStringList("reportlog." + id) : new ArrayList<>();
    }

    public void clearReports(UUID id, String name) {
        data.set("reports." + id, null);
        data.set("reportlog." + id, null);
        this.u.msg(Messages.cleared_reports.replace("%prefix%", Messages.prefix).replace("%name%", name));
    }
}
