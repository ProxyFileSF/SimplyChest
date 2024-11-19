package de.proxyfile.simplyChest.essentials;

import de.proxyfile.simplyChest.SimplyChest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtilityHelper {

    public static void log(String type, String message) {
        boolean debug = SimplyChest.get().getConfig().getBoolean("simplychest.settings.debug");

        if(message != null) {
            String translated = translate(message);
            switch (type) {
                case "fatal":
                        Bukkit.getConsoleSender().sendMessage(SimplyChest.get().prefix + translate("&7[&4&lғᴀᴛᴀʟ&7] &4") + translated);
                case "error":
                    if(debug) { Bukkit.getConsoleSender().sendMessage(SimplyChest.get().prefix + ChatColor.translateAlternateColorCodes('&',"&7[&cᴇʀʀᴏʀ&7] &c") + translated); }
                case "warning":
                    if(debug) { Bukkit.getConsoleSender().sendMessage(SimplyChest.get().prefix + ChatColor.translateAlternateColorCodes('&',"&7[&eᴡᴀʀɴɪɴɢ&7] &e") + translated); }
            }
        }
    }

    public static String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void createDefaults() {
        PreparedStatement ps = QueryHandler.prepareStatement("CREATE TABLE IF NOT EXISTS sc_userdata (UUID VARCHAR(36), USERNAME VARCHAR(16), SIZE INT, INVENTORY JSON)");
        try {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
