package de.proxyfile.simplyChest.essentials;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import de.proxyfile.simplyChest.SimplyChest;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {

    private Connection con;

    private String data;
    private String host;
    private Integer port;
    private String user;
    private String pass;

    public MySQL() { initialize(); }

    public void initialize() {
        MysqlDataSource source = new MysqlConnectionPoolDataSource();

        data = SimplyChest.get().getConfig().getString("mysql.data");
        host = SimplyChest.get().getConfig().getString("mysql.host");
        port = SimplyChest.get().getConfig().getInt("mysql.port");
        user = SimplyChest.get().getConfig().getString("mysql.user");
        pass = SimplyChest.get().getConfig().getString("mysql.pass");

        source.setServerName(host);
        source.setPortNumber(port);
        source.setDatabaseName(data);
        source.setUser(user);
        source.setPassword(pass);

        try {
            con = source.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean status() {
        if(!(con == null)) {
            try {
                if(!con.isClosed()) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public Connection get() {
        return con;
    }

}
