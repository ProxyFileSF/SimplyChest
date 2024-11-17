package de.proxyfile.simplyChest;

import de.proxyfile.simplyChest.essentials.QueryHandler;
import de.proxyfile.simplyChest.essentials.UtilityHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        // Oᴛʜᴇʀs - Sᴇᴄᴛɪᴏɴ
        QueryHandler.request();
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
