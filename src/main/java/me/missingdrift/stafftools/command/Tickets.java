package me.missingdrift.stafftools.command;

import me.missingdrift.stafftools.objects.Ticket;
import me.missingdrift.stafftools.objects.User;
import me.missingdrift.stafftools.utility.Messages;
import me.missingdrift.stafftools.utility.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tickets implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Util.log(Messages.console.replace("%prefix%", Messages.prefix));
            return true;
        }
        User u = new User((Player)sender);
        if (!u.hasPerm("stafftools.tickets")) {
            u.msg(Messages.noperm);
            return true;
        }
        if (args.length != 1 && args.length != 2 && args.length < 2) {
            if (u.hasPerm("stafftools.tickets.manage")) {
                u.msg(Messages.usage.replace("%prefix%",
                        Messages.prefix).replace("%usage%", "/" + label + " <create/list/close/reply> <id/request>"));
            } else {
                u.msg(Messages.usage.replace("%prefix%",
                        Messages.prefix).replace("%usage%", "/" + label + " <create/reply> <request/id>"));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("create") && args.length >= 2) {
            String request = "";
            for (int i = 1; i < args.length; i++)
                request = String.valueOf(request) + " " + args[i];
            Ticket.create(request.trim(), u.getUUID(), u.getName(), u.getPlayer());
        }
        if (args[0].equalsIgnoreCase("reply") && args.length >= 2)
            if (Ticket.getTicket(args[1]) == null) {
                u.msg(Messages.ticket_not_found.replace("%prefix%", Messages.prefix).replace("%id%", args[1]));
            } else {
                String r = "";
                for (int i = 2; i < args.length; i++)
                    r = String.valueOf(r) + " " + args[i];
                if (!Ticket.getTicket(args[1]).getUuid().equals(u.getUUID())) {
                    Ticket.reply(Ticket.getTicket(args[1]), u, r.trim());
                } else {
                    Ticket.replyTicket(Ticket.getTicket(args[1]), u.getPlayer(), r.trim(), Ticket.getTicket(args[1]).getStaff());
                }
            }
        if (u.hasPerm("stafftools.tickets.manage")) {
            if (args[0].equalsIgnoreCase("list") && args.length == 1) {
                if (Ticket.getTicketList(u).size() == 0) {
                    u.msg(Messages.no_tickets_submitted.replace("%prefix%", Messages.prefix));
                    return true;
                }
                u.msg("");
                for (String ticket : Ticket.getTicketList(u))
                    u.msg(Util.c(ticket));
                u.msg("");
            }
            if (args[0].equalsIgnoreCase("close") && args.length == 2)
                if (Ticket.getTicket(args[1]) == null) {
                    u.msg(Messages.ticket_not_found.replace("%prefix%", Messages.prefix).replace("%id%", args[1]));
                } else {
                    Ticket.closeTicket(Ticket.getTicket(args[1]), u);
                }
        }
        return true;
    }
}
