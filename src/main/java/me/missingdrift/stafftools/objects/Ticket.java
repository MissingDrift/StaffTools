package me.missingdrift.stafftools.objects;

import me.missingdrift.stafftools.StaffTools;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Ticket {
    public static List<Ticket> ticketList = new ArrayList<>();

    public static Map<UUID, Ticket> tickets = new HashMap<>();

    public static Map<UUID, Long> cooldown = new HashMap<>();

    public UUID uuid;

    private static User staff = null;

    public String request;

    public String id;

    public String date;

    public String name;

    public Ticket(UUID uuid, String request, String id, String date, String name) {
        this.uuid = uuid;
        this.request = request;
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public User getStaff() {
        return staff;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getDate() {
        return this.date;
    }

    public String getRequest() {
        return this.request;
    }

    public String getId() {
        return this.id;
    }

    public static Ticket getTicket(String id) {
        Ticket result = null;
        for (Ticket t : ticketList) {
            if (t.getId().replace("#", "").equals(id.replace("#", ""))) {
                result = t;
                break;
            }
        }
        return result;
    }

    public static void closeTicket(Ticket t, User u) {
        if (t == null)
            return;
        ticketList.remove(t);
        tickets.remove(t.getUuid());
        Player creator = Bukkit.getPlayer(t.getUuid());
        if (creator != null)
            creator.sendMessage(Messages.your_ticket_deleted.replace("%name%", u.getName()).replace("%id%", t.getId()));
        u.msg(Messages.ticket_closed.replace("%prefix%", Messages.prefix).replace("%id%", t.getId()));
    }

    public static void replyTicket(Ticket t, Player p, String response, User staff) {
        if (staff == null) {
            p.sendMessage(Util.c(Messages.ticket_cannot_reply));
            return;
        }
        if (staff.getUUID().equals(p.getUniqueId())) {
            p.sendMessage(Util.c(Messages.ticket_response_member.replace("%id%", t.getId()).replaceAll("%name%", p.getName()).replace("%response%", response)));
        } else {
            p.sendMessage(Util.c(Messages.ticket_response_member.replace("%id%", t.getId()).replaceAll("%name%", p.getName()).replace("%response%", response)));
            staff.getPlayer().sendMessage(Util.c(Messages.ticket_response_member.replace("%id%", t.getId()).replaceAll("%name%", p.getName()).replace("%response%", response)));
        }
    }

    public static void reply(Ticket t, User u, String response) {
        Player creator = Bukkit.getPlayer(t.getUuid());
        if (creator == null) {
            u.msg(Messages.ticket_creator_offline.replace("%prefix%", Messages.prefix));
            return;
        }
        if (staff != null && t.getStaff().getPlayer() != null && !staff.getUUID().equals(u.getUUID())) {
            u.msg(Messages.ticket_already_claimed.replace("%prefix%", Messages.prefix).replace("%id%", t.getId()).replace("%name%", staff.getName()));
            return;
        }
        if (!creator.getUniqueId().equals(u.getUUID())) {
            u.msg(Util.c(Messages.ticket_response_staff.replace("%id%", t.getId()).replace("%name%", u.getName()).replace("%response%", response)));
            staff = u;
        }
        creator.sendMessage(Util.c(Messages.ticket_response_staff.replace("%id%", t.getId()).replace("%name%", u.getName()).replace("%response%", response)));
    }

    public static List<String> getTicketList(User ignoredU) {
        List<String> list = new ArrayList<>();
        for (Ticket t : ticketList)
            list.add(Messages.ticket_list_format.replace("%id%", t.getId()).replace("%name%", t.getName()).replace("%time%", t.getDate()).replace("%reason%", t.getRequest()));
        return list;
    }

    public static void create(String request, UUID id, String name, Player p) {
        if (cooldown.containsKey(id)) {
            int req = StaffTools.getInstance().getConfig().getInt("ticket-cooldown");
            long rem = cooldown.get(id) / 1000L + req - System.currentTimeMillis() / 1000L;
            if (rem > 0L) {
                p.sendMessage(Util.c(Messages.on_cooldown_message.replace("%prefix%", Messages.prefix).replace("%time%", Long.toString(rem))));
                return;
            }
        }
        int nextId = ticketList.size() + 1;
        Ticket ticket = new Ticket(id, request, "#" + nextId, Util.getCurrentTime(), name);
        tickets.put(id, ticket);
        ticketList.add(ticket);
        p.sendMessage(Util.c(Messages.ticket_created.replace("%request%", request).replace("%id%", ticket.getId())));
        cooldown.put(id, System.currentTimeMillis());
        for (Player player : Bukkit.getOnlinePlayers()) {
            User u = new User(player);
            if (player.hasPermission("stafftools.ticket.notify") && u.isReceivingTicketNotif())
                u.msg(Messages.ticket_submitted_notify.replace("%prefix%", Messages.prefix).replace("%id%", ticket.getId()).replace("%name%", name).replace("%request%", request));
        }
    }
}
