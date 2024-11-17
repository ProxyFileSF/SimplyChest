package de.proxyfile.simplyChest;

import de.proxyfile.simplyChest.essentials.QueryHandler;
import de.proxyfile.simplyChest.essentials.UtilityHelper;
import de.proxyfile.simplyChest.listeners.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class SimplyChest extends JavaPlugin {

    public static SimplyChest plugin;
    public String prefix;

    @Override
    public void onEnable() {
        plugin = this;

        // Cᴏɴғɪɢᴜʀᴀᴛɪᴏɴ - Sᴇᴄᴛɪᴏɴ
        this.saveDefaultConfig();
        prefix = UtilityHelper.translate(getConfig().getString("simplychest.settings.prefix"));

        // Cᴏᴍᴍᴀɴᴅ - Rᴇɢɪsᴛʀʏ

        // Lɪsᴛᴇɴᴇʀ - Rᴇɢɪsᴛʀʏ
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerEvents(), this);

        // Oᴛʜᴇʀs - Sᴇᴄᴛɪᴏɴ
        QueryHandler.request();
        UtilityHelper.createDefaults();
    }

    @Override
    public void onDisable() {

    }

    public static SimplyChest get() {
        return plugin;
    }
    public void unload() {
        getServer().getPluginManager().disablePlugin(this);
    }
}
