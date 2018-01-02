/**
 * All rights by Floxiii (https://Floxiii.net)
 * You are NOT allowed to modify this code
 * You are NOT allowed to use this code in your plugins
 * You are NOT allowed to claim this plugin as your own
 */

class CoinsAPI {

    /**
     * @return true if MySQL is connected
     */
    boolean MySQLIsConnected();

    /**
     * @param UUID {@link UUID}
     * @return true if the player exits
     */
    boolean playerExists(UUID UUID);

    /**
     * @param Username {@link String}
     * @return true if the player exits
     */
    boolean playerExists(String Username);

    /**
     * @param UUID {@link UUID}
     * @return true if the player was created or exits
     */
    boolean createPlayer(UUID UUID);

    /**
     * @param Username {@link String}
     * @return true if the player was created or exits
     */
    boolean createPlayer(String Username);

    /**
     * @param UUID {@link UUID}
     * @return coins of Player by UUID
     */
    Integer getCoins(UUID UUID);

    /**
     * @param Username {@link String}
     * @return coins of Player by Username
     */
    Integer getCoins(String Username);

    /**
     * @param UUID {@link UUID}
     * @param Coins {@link Integer}
     */
    ublic static void setCoins(UUID UUID, Integer Coins);

    /**
     * @param Username {@link String}
     * @param Coins {@link Integer}
     */
    void setCoins(String Username, Integer Coins);

    /**
     * @param UUID {@link UUID}
     * @param Coins {@link Integer}
     */
    boolean addCoins(UUID UUID, Integer Coins);

    /**
     * @param Username {@link String}
     * @param Coins {@link Integer}
     */
    boolean addCoins(String Username, Integer Coins);

    /**
     * @param UUID {@link UUID}
     * @param Coins {@link Integer}
     */
    boolean removeCoins(UUID UUID, Integer Coins);


    /**
     * @param Username {@link String}
     * @param Coins {@link Integer}
     */
    boolean removeCoins(String Username, Integer Coins);

    /**
     * @param UUID {@link UUID}
     * @param Coins {@link Integer}
     * @return true if player has enough coins
     */
    boolean hasEnoughCoins(UUID UUID, Integer Coins);

    /**
     * @param Username {@link String}
     * @param Coins {@link Integer}
     * @return true if player has enough coins
     */
    boolean hasEnoughCoins(String Username, Integer Coins);

    /**
     * @param FromUUID {@link UUID}
     * @param ToUUID {@link UUID}
     * @param Coins {@link Integer}
     *
     * @return true or false if the transaction is completed
     */
    boolean sendCoins(UUID FromUUID, UUID ToUUID, Integer Coins);

    /**
     * @param FromUsername {@link String}
     * @param ToUsername {@link String}
     * @param Coins {@link Integer}
     *
     * @return true or false if the transaction is completed
     */
    boolean sendCoins(String FromUsername, String ToUsername, Integer Coins);

    /**
     * @param UUID {@link UUID}
     * reset the Coins of a Player
     */
    void resetCoins(UUID UUID);

    /**
     * @param Username {@link String}
     * reset the Coins of a Player
     */
    void resetCoins(String Username);

    /**
     * @return preifx of the plugin
     */
    String getPluginPrefix();

    /**
     * @return default Coins
     */
    Integer getDefaultCoins();
}
