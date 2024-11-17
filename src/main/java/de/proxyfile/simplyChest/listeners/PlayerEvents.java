package de.proxyfile.simplyChest.listeners;

import de.proxyfile.simplyChest.essentials.QueryHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player usr = e.getPlayer();
        PreparedStatement userCheck = QueryHandler.prepareStatement("SELECT COUNT(*) FROM sc_userdata WHERE UUID = ?");
        try {
            userCheck.setString(1, usr.getUniqueId().toString());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        if(!QueryHandler.isAvailable(userCheck)) {
            PreparedStatement ps = QueryHandler.prepareStatement("INSERT INTO sc_userdata (UUID, USERNAME) VALUES(?,?)");
            try {
                ps.setString(1, usr.getUniqueId().toString());
                ps.setString(2, usr.getName());
                ps.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
