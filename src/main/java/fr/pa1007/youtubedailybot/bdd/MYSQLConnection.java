package fr.pa1007.youtubedailybot.bdd;

import fr.pa1007.youtubedailybot.statistics.Stats;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    public void addStat(Stats s) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        if (!isInBase(s)) {
            PreparedStatement p = connection.prepareStatement(Constants.START_STATS);
            p.setString(1, s.getChanID());
            p.setLong(2, s.getCumulatedViews());
            p.setLong(3, s.getCumulatedLikes());
            p.setLong(4, s.getCumulatedComms());
            p.setLong(5, s.getCumulatedDisLikes());
            p.setInt(6, Calendar.getInstance().get(Calendar.MONTH) + 1);
            p.execute();
            if (!nameInBase(s.getChanID(), s.getChanName())) {
                addName(s);
            }
            p.close();
        }
        else {
            sumStats(s);
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

    private boolean isInBase(Stats s) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        PreparedStatement p = connection.prepareStatement(Constants.EXIST_STATS);
        p.setString(1, s.getChanID());
        p.setInt(2, Calendar.getInstance().get(Calendar.MONTH) + 1);
        ResultSet rs = p.executeQuery();
        rs.first();
        return rs.getBoolean(1);
    }

    private void addName(Stats s) throws SQLException, ClassNotFoundException {
        addName(s.getChanID(), s.getChanName());
    }

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

    private boolean nameInBase(String chanID, String chanName) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        PreparedStatement p = connection.prepareStatement(Constants.TEST_NAME);
        p.setString(1, chanID);
        p.setString(2, chanName);
        ResultSet rs = p.executeQuery();
        rs.first();
        return rs.getBoolean(1);
    }


    private void sumStats(Stats s) throws SQLException, ClassNotFoundException {
        testIfConnectionOpened();
        PreparedStatement p = connection.prepareStatement(Constants.UPDATE_STATS);
        p.setLong(1, s.getCumulatedViews());
        p.setLong(2, s.getCumulatedViews());
        p.setLong(3, s.getCumulatedDisLikes());
        p.setLong(4, s.getCumulatedComms());
        p.setLong(5, s.getNumber());
        p.setString(6, s.getChanID());
        p.setInt(7, Calendar.getInstance().get(Calendar.MONTH) + 1);
        p.execute();
        p.close();
    }

    private void testIfConnectionOpened() throws SQLException, ClassNotFoundException {
        if (!connected) {
            getConnection();
        }
    }
}
