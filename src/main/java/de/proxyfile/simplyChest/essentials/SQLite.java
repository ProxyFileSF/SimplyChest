package de.proxyfile.simplyChest.essentials;

import de.proxyfile.simplyChest.SimplyChest;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {

    private Connection con;
    private File file;

    public SQLite() {
        initialize();
    }

    public void initialize() {
        file = new File(SimplyChest.get().getDataFolder(), "data.db");

        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + file);
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
