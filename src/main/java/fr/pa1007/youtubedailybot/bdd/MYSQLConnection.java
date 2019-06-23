package fr.pa1007.youtubedailybot.bdd;

import fr.pa1007.youtubedailybot.statistics.Stats;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Mysql connection.
 */
public class MYSQLConnection {

    private Connection connection;
    private boolean    connected;


    public MYSQLConnection() {
    }

    public Map<String, Stats> getAllInfos() throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        Map<String, Stats> infos = new HashMap<>();
        PreparedStatement  p     = connection.prepareStatement(Constants.GET_ALL_STATS);
        p.setInt(1, Calendar.getInstance().get(Calendar.MONTH) + 1);
        ResultSet rs = p.executeQuery();
        rs.beforeFirst();
        while (rs.next()) {
            PreparedStatement p2 = connection.prepareStatement(Constants.GET_CHAN_NAME);
            p2.setString(1, rs.getString(1));
            ResultSet rs2 = p2.executeQuery();
            rs2.first();
            Stats s = new Stats(
                    rs2.getString(1),
                    rs.getString(1),
                    rs.getLong(2),
                    rs.getLong(3),
                    rs.getLong(4),
                    rs.getLong(5),
                    rs.getLong(6)
            );
            infos.put(rs.getString(1), s);
        }
        p.close();
        return infos;
    }

    /**
     * Add stat to the Database.
     *
     * @param stats the stats
     * @throws SQLException           if the program is unable to connect to the database
     * @throws ClassNotFoundException if the jdbc driver is not present
     */
    public void addStat(Stats stats) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        if (!isInBase(stats)) {
            PreparedStatement p = connection.prepareStatement(Constants.START_STATS);
            p.setString(1, stats.getChanID());
            p.setLong(2, stats.getCumulatedViews());
            p.setLong(3, stats.getCumulatedLikes());
            p.setLong(4, stats.getCumulatedComms());
            p.setLong(5, stats.getCumulatedDisLikes());
            p.setInt(6, Calendar.getInstance().get(Calendar.MONTH) + 1);
            p.execute();
            if (!nameInBase(stats.getChanID(), stats.getChanName())) {
                addName(stats);
            }
            p.close();
        }
        else {
            sumStats(stats);
        }
    }

    /**
     * to get a new Connection
     *
     * @throws SQLException           if the program is unable to connect to the database
     * @throws ClassNotFoundException if the jdbc driver is not present
     */
    private void getConnection() throws SQLException, ClassNotFoundException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Class.forName("com.mysql.jdbc.Driver");
        if (!connected || !connection.isValid(1)) {
            connection = DriverManager.getConnection(Constants.URL, Constants.USER_NAME, Constants.USER_PASSWORD);
            connected = true;
        }

    }

    /**
     * @param stats the stats to check
     * @return true if in base or false if not
     * @throws SQLException           if the program is unable to connect to the database
     * @throws ClassNotFoundException if the jdbc driver is not present
     */
    private boolean isInBase(Stats stats) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        PreparedStatement p = connection.prepareStatement(Constants.EXIST_STATS);
        p.setString(1, stats.getChanID());
        p.setInt(2, Calendar.getInstance().get(Calendar.MONTH) + 1);
        ResultSet rs = p.executeQuery();
        rs.first();
        return rs.getBoolean(1);
    }

    /**
     * To add a stats
     *
     * @param stats to add
     * @throws SQLException           if the program is unable to connect to the database
     * @throws ClassNotFoundException if the jdbc driver is not present
     * @see #addName(String, String)
     */
    private void addName(Stats stats) throws SQLException, ClassNotFoundException {
        addName(stats.getChanID(), stats.getChanName());
    }

    /**
     * To add a channel and is name
     *
     * @param chanID   the id of the channel
     * @param chanName the name of the channel
     * @throws SQLException           if the program is unable to connect to the database
     * @throws ClassNotFoundException if the jdbc driver is not present
     */
    private void addName(String chanID, String chanName) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        if (!nameInBase(chanID, chanName)) {
            PreparedStatement p = connection.prepareStatement(Constants.ADD_NAME);
            p.setString(1, chanID);
            p.setString(2, chanName);
            p.execute();
            p.close();
        }
    }

    /**
     * to see if there ar in the database
     *
     * @param chanID   the id of the channel
     * @param chanName the name of the channel
     * @return
     * @throws SQLException           if the program is unable to connect to the database
     * @throws ClassNotFoundException if the jdbc driver is not present
     */
    private boolean nameInBase(String chanID, String chanName) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        PreparedStatement p = connection.prepareStatement(Constants.TEST_NAME);
        p.setString(1, chanID);
        p.setString(2, chanName);
        ResultSet rs = p.executeQuery();
        rs.first();
        return rs.getBoolean(1);
    }


    /**
     * Update the Database with the new value
     *
     * @param stats the stats to update
     * @throws SQLException           if the program is unable to connect to the database
     * @throws ClassNotFoundException if the driver is not here
     */
    private void sumStats(Stats stats) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        PreparedStatement p = connection.prepareStatement(Constants.UPDATE_STATS);
        p.setLong(1, stats.getCumulatedViews());
        p.setLong(2, stats.getCumulatedLikes());
        p.setLong(3, stats.getCumulatedDisLikes());
        p.setLong(4, stats.getCumulatedComms());
        p.setLong(5, stats.getNumber());
        p.setString(6, stats.getChanID());
        p.setInt(7, Calendar.getInstance().get(Calendar.MONTH) + 1);
        p.execute();
        p.close();
    }

    private void testIfConnectionOpened() throws SQLException, ClassNotFoundException {
        getConnection();
    }
}
