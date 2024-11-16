package de.proxyfile.simplyChest.essentials;

import de.proxyfile.simplyChest.SimplyChest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class UtilityHelper {

    public static void log(String type, String message) {
        if(message != null) {
            String translated = ChatColor.translateAlternateColorCodes('&', message);
            switch (type) {
                case "fatal":
                    Bukkit.getConsoleSender().sendMessage(SimplyChest.get().prefix + "&7[&4&lғᴀᴛᴀʟ&7] &4" + translated);
                case "error":
                    Bukkit.getConsoleSender().sendMessage(SimplyChest.get().prefix + "&7[&cᴇʀʀᴏʀ&7] &c" + translated);
                case "warning":
                    Bukkit.getConsoleSender().sendMessage(SimplyChest.get().prefix + "&7[&eᴡᴀʀɴɪɴɢ&7] &e" + translated);
            }
        }
    }

}
