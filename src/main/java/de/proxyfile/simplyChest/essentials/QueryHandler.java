package de.proxyfile.simplyChest.essentials;

import de.proxyfile.simplyChest.SimplyChest;

import java.util.ArrayList;
import java.util.List;

public class QueryHandler {
    private static String currentMethod;

    private static MySQL mysql;
    private static SQLite sqlite;

    public static void request() {
        boolean statusMySQL = SimplyChest.get().getConfig().getBoolean("mysql.use");
        boolean statusSQLite = SimplyChest.get().getConfig().getBoolean("sqlite.use");

        if(!((statusMySQL && statusSQLite) || (!statusMySQL && !statusSQLite))) {
            if(statusMySQL) {
                mysql = new MySQL();
                currentMethod = "MySQL";
            } else {
                sqlite = new SQLite();
                currentMethod = "SQLite";
            }
        } else {
            UtilityHelper.log("fatal", "No connection method found! Please update the values in the 'config.yml' file. Consequently, this plugin will be unloaded.");
            SimplyChest.get().unload();
        }
    }

    public static void createStatement() {
        if(mysql == null || sqlite == null) {
            if(currentMethod.equals("MySQL")) {

            } else if(currentMethod.equals("SQLite")) {

            }
        } else {
            UtilityHelper.log("error", "The QueryHandler was not initialized at the time of execution. Please restart the server to resolve this issue.");
        }
    }

}
