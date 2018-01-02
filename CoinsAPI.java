package net.Floxiii.CoinsAPI.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import net.Floxiii.CoinsAPI.FloxiiiMain;
import net.Floxiii.CoinsAPI.FloxiiiUUIDFetcher;
import net.Floxiii.CoinsAPI.api.events.CoinsChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * All rights by Floxiii (https://Floxiii.net)
 */

public class CoinsAPI extends JavaPlugin {


    /**
     * @return true if MySQL is connected
     */
    public static boolean MySQLIsConnected() {
        return FloxiiiMain.mysql.isConnected();
    }

    /**
     * @param UUID {@link UUID}
     * @return true if the player exits
     */
    public static boolean playerExists(UUID UUID) {
        if(CoinsAPI.MySQLIsConnected()) {
            try {
                ResultSet rs = FloxiiiMain.mysql.query("SELECT * FROM CoinsAPI WHERE UUID= '" + UUID + "'");

                if(rs.next()) {
                    return rs.getString("UUID") != null;
                }
                return false;
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Bukkit.getConsoleSender().sendMessage("[" + FloxiiiMain.instance.getDescription().getName() + "] §cError while connecting to the database!");
        }
        return false;
    }

    /**
     * @param Username {@link String}
     * @return true if the player exits
     */
    public static boolean playerExists(String Username) {
        UUID UUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(Username));
        if(CoinsAPI.MySQLIsConnected()) {
            try {
                ResultSet rs = FloxiiiMain.mysql.query("SELECT * FROM CoinsAPI WHERE UUID= '" + UUID + "'");

                if(rs.next()) {
                    return rs.getString("UUID") != null;
                }
                return false;
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Bukkit.getConsoleSender().sendMessage("[" + FloxiiiMain.instance.getDescription().getName() + "] §cError while connecting to the database!");
        }
        return false;
    }

    /**
     * @param UUID {@link UUID}
     * @return true if the player was created or exits
     */
    public static boolean createPlayer(UUID UUID) {
        if(CoinsAPI.MySQLIsConnected()) {
            if(!playerExists(UUID)) {
                FloxiiiMain.mysql.update("INSERT INTO CoinsAPI(UUID, Coins) VALUES ('" + UUID + "', '" + getDefaultCoins() + "');");
            }
            return true;
        }else{
            Bukkit.getConsoleSender().sendMessage("[" + FloxiiiMain.instance.getDescription().getName() + "] §cError while connecting to the database!");
        }
        return false;
    }

    /**
     * @param Username {@link String}
     * @return true if the player was created or exits
     */
    public static boolean createPlayer(String Username) {
        UUID UUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(Username));
        if(CoinsAPI.MySQLIsConnected()) {
            if(!playerExists(UUID)) {
                FloxiiiMain.mysql.update("INSERT INTO CoinsAPI(UUID, Coins) VALUES ('" + UUID + "', '" + getDefaultCoins() + "');");
            }
            return true;
        }else{
            Bukkit.getConsoleSender().sendMessage("[" + FloxiiiMain.instance.getDescription().getName() + "] §cError while connecting to the database!");
        }
        return false;
    }

    /**
     * @param UUID {@link UUID}
     * @return coins of Player by UUID
     */
    public static Integer getCoins(UUID UUID) {
        createPlayer(UUID);
        Integer i = 0;
        try {
            ResultSet rs = FloxiiiMain.mysql.query("SELECT * FROM CoinsAPI WHERE UUID= '" + UUID + "'");
            if((!rs.next()) || (Integer.valueOf(rs.getInt("Coins")) == null));
            i = rs.getInt("Coins");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @param Username {@link String}
     * @return coins of Player by Username
     */
    public static Integer getCoins(String Username) {
        UUID UUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(Username));
        createPlayer(UUID);
        Integer i = 0;
        try {
            ResultSet rs = FloxiiiMain.mysql.query("SELECT * FROM CoinsAPI WHERE UUID= '" + UUID + "'");
            if((!rs.next()) || (Integer.valueOf(rs.getInt("Coins")) == null));
            i = rs.getInt("Coins");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @param UUID {@link UUID}
     * @param Coins {@link Integer}
     */
    public static void setCoins(UUID UUID, Integer Coins) {
        Integer OldCoins = getCoins(UUID);
        createPlayer(UUID);
        FloxiiiMain.mysql.update("UPDATE CoinsAPI SET Coins= '" + Coins + "' WHERE UUID= '" + UUID + "';");
        Bukkit.getPluginManager().callEvent(new CoinsChangeEvent(UUID, Coins, OldCoins));
    }

    /**
     * @param Username {@link String}
     * @param Coins {@link Integer}
     */
    public static void setCoins(String Username, Integer Coins) {
        Integer OldCoins = getCoins(Username);
        UUID UUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(Username));
        createPlayer(UUID);
        FloxiiiMain.mysql.update("UPDATE CoinsAPI SET Coins= '" + Coins + "' WHERE UUID= '" + UUID + "';");
        Bukkit.getPluginManager().callEvent(new CoinsChangeEvent(UUID, Coins, OldCoins));
    }

    /**
     * @param UUID {@link UUID}
     * @param Coins {@link Integer}
     */
    public static boolean addCoins(UUID UUID, Integer Coins) {
        createPlayer(UUID);
        if(Coins > 0) {
            setCoins(UUID, Integer.valueOf(getCoins(UUID) + Coins));
            return true;
        }
        return false;
    }

    /**
     * @param Username {@link String}
     * @param Coins {@link Integer}
     */
    public static boolean addCoins(String Username, Integer Coins) {
        UUID UUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(Username));
        createPlayer(UUID);
        if(Coins > 0) {
            setCoins(UUID, Integer.valueOf(getCoins(UUID) + Coins));
            return true;
        }
        return false;
    }

    /**
     * @param UUID {@link UUID}
     * @param Coins {@link Integer}
     */
    public static boolean removeCoins(UUID UUID, Integer Coins) {
        createPlayer(UUID);
        if(getCoins(UUID) - Coins >= 0) {
            setCoins(UUID, Integer.valueOf(getCoins(UUID) - Coins));
            return true;
        }else{
            setCoins(UUID, 0);
            return true;
        }
    }


    /**
     * @param Username {@link String}
     * @param Coins {@link Integer}
     */
    public static boolean removeCoins(String Username, Integer Coins) {
        UUID UUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(Username));
        createPlayer(UUID);
        if(getCoins(UUID) - Coins >= 0) {
            setCoins(UUID, Integer.valueOf(getCoins(UUID) - Coins));
            return true;
        }else{
            setCoins(UUID, 0);
            return true;
        }
    }

    /**
     * @param UUID {@link UUID}
     * @param Coins {@link Integer}
     * @return true if player has enough coins
     */
    public static boolean hasEnoughCoins(UUID UUID, Integer Coins) {
        if(getCoins(UUID) >= Coins) {
            return true;
        }
        return false;
    }

    /**
     * @param Username {@link String}
     * @param Coins {@link Integer}
     * @return true if player has enough coins
     */
    public static boolean hasEnoughCoins(String Username, Integer Coins) {
        UUID UUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(Username));
        if(getCoins(UUID) >= Coins) {
            return true;
        }
        return false;
    }

    /**
     * @param FromUUID {@link UUID}
     * @param ToUUID {@link UUID}
     * @param Coins {@link Integer}
     *
     * @return true or false if the transaction is completed
     */
    public static boolean sendCoins(UUID FromUUID, UUID ToUUID, Integer Coins) {
        if(getCoins(FromUUID) >= Coins) {
            removeCoins(FromUUID, Coins);
            addCoins(ToUUID, Coins);
            return true;
        }
        return false;
    }

    /**
     * @param FromUsername {@link String}
     * @param ToUsername {@link String}
     * @param Coins {@link Integer}
     *
     * @return true or false if the transaction is completed
     */
    public static boolean sendCoins(String FromUsername, String ToUsername, Integer Coins) {
        UUID FromUUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(FromUsername));
        UUID ToUUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(ToUsername));
        if(getCoins(FromUUID) >= Coins) {
            removeCoins(FromUUID, Coins);
            addCoins(ToUUID, Coins);
            return true;
        }
        return false;
    }

    /**
     * @param UUID {@link UUID}
     * reset the Coins of a Player
     */
    public static void resetCoins(UUID UUID) {
        createPlayer(UUID);
        setCoins(UUID, FloxiiiMain.defaultCoins);
    }

    /**
     * @param Username {@link String}
     * reset the Coins of a Player
     */
    public static void resetCoins(String Username) {
        UUID UUID = java.util.UUID.fromString(FloxiiiUUIDFetcher.getUUID(Username));
        createPlayer(UUID);
        setCoins(UUID, FloxiiiMain.defaultCoins);
    }

    /**
     * @return preifx of the plugin
     */
    public static String getPluginPrefix() {
        return FloxiiiMain.prefix;
    }

    /**
     * @return default Coins
     */
    public static Integer getDefaultCoins() {
        return FloxiiiMain.defaultCoins;
    }
}