package me.missingdrift.stafftools.utility;

import me.missingdrift.stafftools.StaffTools;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public interface Messages {
    public static final FileConfiguration cfg = StaffTools.getInstance().getConfig();

    public static final String prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("prefix"));

    public static final String console = ChatColor.translateAlternateColorCodes('&', cfg.getString("must-be-player"));

    public static final String noperm = ChatColor.translateAlternateColorCodes('&', cfg.getString("no-permission"));

    public static final String usage = ChatColor.translateAlternateColorCodes('&', cfg.getString("usage"));

    public static final String unsupported_version = ChatColor.translateAlternateColorCodes('&', cfg.getString("unsupported-version"));

    public static final String staff_chat_enabled = ChatColor.translateAlternateColorCodes('&', cfg.getString("staff-chat-enabled"));

    public static final String staff_chat_disabled = ChatColor.translateAlternateColorCodes('&', cfg.getString("staff-chat-disabled"));

    public static final String target_is_offline = ChatColor.translateAlternateColorCodes('&', cfg.getString("target-is-offline"));

    public static final String player_reported = ChatColor.translateAlternateColorCodes('&', cfg.getString("player-reported"));

    public static final String reported_notification = ChatColor.translateAlternateColorCodes('&', cfg.getString("reported-notification"));

    public static final String on_cooldown_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("on-cooldown-message"));

    public static final String check_report_info = ChatColor.translateAlternateColorCodes('&', cfg.getString("check-report-info"));

    public static final String cleared_reports = ChatColor.translateAlternateColorCodes('&', cfg.getString("cleared-reports"));

    public static final String chat_cleared = ChatColor.translateAlternateColorCodes('&', cfg.getString("chat-cleared"));

    public static final String chat_slowed = ChatColor.translateAlternateColorCodes('&', cfg.getString("chat-slowed"));

    public static final String chat_slow_disabled = ChatColor.translateAlternateColorCodes('&', cfg.getString("chat-slow-disabled"));

    public static final String chat_locked_speak = ChatColor.translateAlternateColorCodes('&', cfg.getString("chat-locked-speak"));

    public static final String chat_lock_enabled = ChatColor.translateAlternateColorCodes('&', cfg.getString("chat-lock-enabled"));

    public static final String chat_lock_disabled = ChatColor.translateAlternateColorCodes('&', cfg.getString("chat-lock-disabled"));

    public static final String argument_isnt_number = ChatColor.translateAlternateColorCodes('&', cfg.getString("argument-isnt-number"));

    public static final String clear_lag_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("clear-lag-message"));

    public static final String commandspy_toggled = ChatColor.translateAlternateColorCodes('&', cfg.getString("commandspy-toggled"));

    public static final String commandspy_format = ChatColor.translateAlternateColorCodes('&', cfg.getString("commandspy-format"));

    public static final String staffmode_toggled = ChatColor.translateAlternateColorCodes('&', cfg.getString("staffmode-toggled"));

    public static final String toggled_players_staffmode = ChatColor.translateAlternateColorCodes('&', cfg.getString("toggled-players-staffmode"));

    public static final String teleport_random_successful = ChatColor.translateAlternateColorCodes('&', cfg.getString("teleport-successful"));

    public static final String teleport_random_fail = ChatColor.translateAlternateColorCodes('&', cfg.getString("teleport-random-fail"));

    public static final String frozen_player_quit = ChatColor.translateAlternateColorCodes('&', cfg.getString("frozen-player-quit"));

    public static final String frozen_player = ChatColor.translateAlternateColorCodes('&', cfg.getString("frozen-player"));

    public static final String frozen_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("frozen-message"));

    public static final String unfroze_player = ChatColor.translateAlternateColorCodes('&', cfg.getString("unfroze-player"));

    public static final String unfrozen_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("unfrozen-message"));

    public static final String miner_list_gui_name = ChatColor.translateAlternateColorCodes('&', cfg.getString("miner-list-gui-name"));

    public static final String staff_list_gui_name = ChatColor.translateAlternateColorCodes('&', cfg.getString("staff-list-gui-name"));

    public static final String player_inspect_gui_name = ChatColor.translateAlternateColorCodes('&', cfg.getString("player-inspect-gui-name"));

    public static final String toggled_vanish = ChatColor.translateAlternateColorCodes('&', cfg.getString("toggled-vanish"));

    public static final String toggled_players_vanish = ChatColor.translateAlternateColorCodes('&', cfg.getString("toggled-players-vanish"));

    public static final String anti_caps_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("anti-caps-message"));

    public static final String chatcolor_list_msg = ChatColor.translateAlternateColorCodes('&', cfg.getString("chatcolor-list-msg"));

    public static final String chatcolor_changed = ChatColor.translateAlternateColorCodes('&', cfg.getString("chatcolor-changed"));

    public static final String chatcolor_changed_target = ChatColor.translateAlternateColorCodes('&', cfg.getString("chatcolor-changed-target"));

    public static final String follower_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("follower-message"));

    public static final String added_note = ChatColor.translateAlternateColorCodes('&', cfg.getString("added-note"));

    public static final String cleared_notes = ChatColor.translateAlternateColorCodes('&', cfg.getString("cleared-notes"));

    public static final String doesnt_have_notes = ChatColor.translateAlternateColorCodes('&', cfg.getString("doesnt-have-notes"));

    public static final String checknote_format = ChatColor.translateAlternateColorCodes('&', cfg.getString("checknote-format"));

    public static final String plugin_reloaded_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("plugin-reloaded-message"));

    public static final String toggled_players_staffchat = ChatColor.translateAlternateColorCodes('&', cfg.getString("toggled-players-staffchat"));

    public static final String must_be_holding_item = ChatColor.translateAlternateColorCodes('&', cfg.getString("must-be-holding-item"));

    public static final String lore_changed = ChatColor.translateAlternateColorCodes('&', cfg.getString("lore-changed"));

    public static final String name_changed = ChatColor.translateAlternateColorCodes('&', cfg.getString("name-changed"));

    public static final String item_meta_reset = ChatColor.translateAlternateColorCodes('&', cfg.getString("item-meta-reset"));

    public static final String enchanted_item = ChatColor.translateAlternateColorCodes('&', cfg.getString("enchanted-item"));

    public static final String enchantments_cleared = ChatColor.translateAlternateColorCodes('&', cfg.getString("enchantments-cleared"));

    public static final String enchantment_not_found = ChatColor.translateAlternateColorCodes('&', cfg.getString("enchantment-not-found"));

    public static final String xray_alert_format = ChatColor.translateAlternateColorCodes('&', cfg.getString("xray-alert-format"));

    public static final String xray_alerts_toggled = ChatColor.translateAlternateColorCodes('&', cfg.getString("xray-alerts-toggled"));

    public static final String xray_alerts_toggled_player = ChatColor.translateAlternateColorCodes('&', cfg.getString("xray-alerts-toggled-player"));

    public static final String swearing_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("swearing-message"));

    public static final String swear_word_list = ChatColor.translateAlternateColorCodes('&', cfg.getString("swear-word-list"));

    public static final String swear_word_added = ChatColor.translateAlternateColorCodes('&', cfg.getString("swear-word-added"));

    public static final String swear_word_removed = ChatColor.translateAlternateColorCodes('&', cfg.getString("swear-word-removed"));

    public static final String bad_command_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("bad-command-message"));

    public static final String commandblocker_added_command = ChatColor.translateAlternateColorCodes('&', cfg.getString("commandblocker-added-command"));

    public static final String commandblocker_removed_command = ChatColor.translateAlternateColorCodes('&', cfg.getString("commandblocker-removed-command"));

    public static final String commandblocker_listed_commands = ChatColor.translateAlternateColorCodes('&', cfg.getString("commandblocker-listed-commands"));

    public static final String mention_alerts_toggled = ChatColor.translateAlternateColorCodes('&', cfg.getString("mention-alerts-toggled"));

    public static final String reportlog_format = ChatColor.translateAlternateColorCodes('&', cfg.getString("reportlog-format"));

    public static final String register_success = ChatColor.translateAlternateColorCodes('&', cfg.getString("register-success"));

    public static final String register_fail = ChatColor.translateAlternateColorCodes('&', cfg.getString("register-success"));

    public static final String login_fail = ChatColor.translateAlternateColorCodes('&', cfg.getString("login-fail"));

    public static final String login_success = ChatColor.translateAlternateColorCodes('&', cfg.getString("login-success"));

    public static final String must_register_msg = ChatColor.translateAlternateColorCodes('&', cfg.getString("sa-register-message"));

    public static final String must_login_msg = ChatColor.translateAlternateColorCodes('&', cfg.getString("sa-login-message"));

    public static final String found_password = ChatColor.translateAlternateColorCodes('&', cfg.getString("found-password"));

    public static final String changed_password = ChatColor.translateAlternateColorCodes('&', cfg.getString("changed-password"));

    public static final String enchantment_list = ChatColor.translateAlternateColorCodes('&', cfg.getString("enchantment-list"));

    public static final String ticket_submitted_notify = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-submitted-notify"));

    public static final String ticket_created = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-created"));

    public static final String ticket_closed = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-closed"));

    public static final String ticket_creator_offline = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-creator-offline"));

    public static final String ticket_response_staff = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-response-staff"));

    public static final String ticket_list_format = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-list-format"));

    public static final String ticket_not_found = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-not-found"));

    public static final String no_tickets_submitted = ChatColor.translateAlternateColorCodes('&', cfg.getString("no-tickets-submitted"));

    public static final String ticket_alerts_toggled = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-alerts-toggled"));

    public static final String ticket_cannot_reply = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-cannot-reply"));

    public static final String ticket_response_member = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-response-creator"));

    public static final String your_ticket_deleted = ChatColor.translateAlternateColorCodes('&', cfg.getString("your-ticket-deleted"));

    public static final String ticket_already_claimed = ChatColor.translateAlternateColorCodes('&', cfg.getString("ticket-is-already-claimed"));

    public static final String whitelist_toggled = ChatColor.translateAlternateColorCodes('&', cfg.getString("whitelist-toggled"));

    public static final String whitelist_required = ChatColor.translateAlternateColorCodes('&', cfg.getString("whitelist-required"));

    public static final String whitelisted_player = ChatColor.translateAlternateColorCodes('&', cfg.getString("whitelisted-player"));

    public static final String whitelisted_user_list = ChatColor.translateAlternateColorCodes('&', cfg.getString("whitelisted-user-list"));

    public static final String player_isnt_whitelisted = ChatColor.translateAlternateColorCodes('&', cfg.getString("player-isnt-whitelisted"));

    public static final String unwhitelisted_player = ChatColor.translateAlternateColorCodes('&', cfg.getString("unwhitelisted-player"));

    public static final String player_already_whitelisted = ChatColor.translateAlternateColorCodes('&', cfg.getString("player-already-whitelisted"));

    public static final String cannot_unwhitelist_player = ChatColor.translateAlternateColorCodes('&', cfg.getString("cannot-unwhitelist"));

    public static final String cant_join_not_whitelisted_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("cant-join-not-whitelisted-message"));

    public static final String cant_find_inventory = ChatColor.translateAlternateColorCodes('&', cfg.getString("cant-find-inventory"));

    public static final String inventory_rollbacked = ChatColor.translateAlternateColorCodes('&', cfg.getString("inventory-rollbacked"));

    public static final String inventory_rollbacked_staff = ChatColor.translateAlternateColorCodes('&', cfg.getString("inventory-rollbacked-staff"));

    public static final String reports_page_doesnt_exist = ChatColor.translateAlternateColorCodes('&', cfg.getString("reports-page-doesnt-exist"));

    public static final String playtime_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("playtime-message"));

    public static final String number_too_low = ChatColor.translateAlternateColorCodes('&', cfg.getString("number-too-low"));

    public static final String no_logs_found = ChatColor.translateAlternateColorCodes('&', cfg.getString("no-logs-found"));

    public static final String logs_cleared = ChatColor.translateAlternateColorCodes('&', cfg.getString("logs-cleared"));

    public static final String logcommand_exists = ChatColor.translateAlternateColorCodes('&', cfg.getString("logcommand-exists"));

    public static final String logcommand_doesnt_exist = ChatColor.translateAlternateColorCodes('&', cfg.getString("logcommand-doesnt-exist"));

    public static final String logcommand_added = ChatColor.translateAlternateColorCodes('&', cfg.getString("logcommand-added"));

    public static final String logcommand_removed = ChatColor.translateAlternateColorCodes('&', cfg.getString("logcommand-removed"));

    public static final String logcommand_list = ChatColor.translateAlternateColorCodes('&', cfg.getString("logcommand-list"));

    public static final String logcommand_format = ChatColor.translateAlternateColorCodes('&', cfg.getString("log-format"));

    public static final String must_be_console = ChatColor.translateAlternateColorCodes('&', cfg.getString("must-be-console"));

    public static final String frozen_prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("frozen-prefix"));

    public static final String report_alerts_toggled = ChatColor.translateAlternateColorCodes('&', cfg.getString("report-alerts-toggled"));

    public static final String player_stripped = ChatColor.translateAlternateColorCodes('&', cfg.getString("player-stripped"));

    public static final String player_inventory_full = ChatColor.translateAlternateColorCodes('&', cfg.getString("player-inventory-full"));

    public static final String item_fixed_msg = ChatColor.translateAlternateColorCodes('&', cfg.getString("item-fixed-msg"));

    public static final String number_too_big = ChatColor.translateAlternateColorCodes('&', cfg.getString("number-too-big"));

    public static final String walk_speed_set = ChatColor.translateAlternateColorCodes('&', cfg.getString("walk-speed-set"));

    public static final String fly_speed_set = ChatColor.translateAlternateColorCodes('&', cfg.getString("fly-speed-set"));

    public static final String walk_speed_reset = ChatColor.translateAlternateColorCodes('&', cfg.getString("walk-speed-reset"));

    public static final String fly_speed_reset = ChatColor.translateAlternateColorCodes('&', cfg.getString("fly-speed-reset"));

    public static final String ping_message = ChatColor.translateAlternateColorCodes('&', cfg.getString("ping-message"));

    public static final String toggled_godmode = ChatColor.translateAlternateColorCodes('&', cfg.getString("toggled-godmode"));

    public static final String toggled_godmode_player = ChatColor.translateAlternateColorCodes('&', cfg.getString("toggled-godmode-player"));
}
