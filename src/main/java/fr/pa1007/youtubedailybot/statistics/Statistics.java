package fr.pa1007.youtubedailybot.statistics;

import com.google.common.base.Objects;
import fr.pa1007.youtubedailybot.bdd.MYSQLConnection;
import fr.pa1007.youtubedailybot.youtube.Video;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public final class Statistics {

    private Map<String, Stats>              map;
    private Map<String, Map<String, Stats>> monthMap;
    private MYSQLConnection                 mysqlConnection;

    public Statistics() throws SQLException, ClassNotFoundException {
        map = new HashMap<>();
        mysqlConnection = new MYSQLConnection();
        init(mysqlConnection.getAllInfos());
    }

    /**
     * Add new video.
     *
     * @param video the video
     * @throws SQLException           the sql exception
     * @throws ClassNotFoundException the class not found exception
     */
    public void addNew(Video video) throws SQLException, ClassNotFoundException {
        Stats s = test(video.getChanID());
        if (s == null) {
            s = new Stats(video);
            map.put(video.getChanID(), s);
        }
        else {
            s.add(video);
        }
        mysqlConnection.addStat(s);
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    public Map<String, Stats> getMap() {
        return map;
    }

    /**
     * Reset month.
     */
    public void resetMonth() {
        String[] monthName = DateFormatSymbols.getInstance().getMonths();
        Calendar cal       = Calendar.getInstance();
        String   month     = monthName[cal.get(Calendar.MONTH)];

        monthMap.put(month, new HashMap<>(map));
        map.clear();
    }

    /**
     * Gets first this month.
     *
     * @param readLine the read line
     * @return the first this month
     * @throws VideoException the video exception
     */
    public Stats getFirstThisMonth(String readLine) throws VideoException {
        Stats s;
        switch (readLine) {
            case "FIRST LINE : AVGVIEWS":
                s = getFirstThisMonthAVGView();
                break;
            case "FIRST LINE : AVGLIKES":
                s = getFirstThisMonthAVGLikes();
                break;
            case "FIRST LINE : TOTLIKE":
                s = getFirstThisMonthLikes();
                break;
            case "FIRST LINE : TOTVIEWS":
                s = getFirstThisMonthViews();
                break;
            default:
                s = getFirstThisMonthNumber();
                break;
        }

        if (s == null) {
            throw new VideoException("Stats Exception");
        }
        else {
            return s;
        }
    }

    /**
     * Gets first this month views.
     *
     * @return the first this month views
     */
    private Stats getFirstThisMonthViews() {
        long  max  = Long.MIN_VALUE;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getCumulatedViews() > max) {
                keep = s;
            }
        }
        return keep;
    }

    /**
     * Gets first this month average view.
     *
     * @return the first this month average view
     */
    private Stats getFirstThisMonthAVGView() {
        long  max  = Long.MIN_VALUE;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getAvgViews() > max) {
                keep = s;
            }
        }
        return keep;
    }

    /**
     * Gets first this month average likes.
     *
     * @return the first this month average likes
     */
    private Stats getFirstThisMonthAVGLikes() {
        long  max  = Long.MIN_VALUE;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getAvgLikes() > max) {
                keep = s;
            }
        }
        return keep;
    }

    /**
     * Gets first this month likes.
     *
     * @return the first this month likes
     */
    private Stats getFirstThisMonthLikes() {
        long  max  = Long.MIN_VALUE;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getCumulatedLikes() > max) {
                keep = s;
            }
        }
        return keep;
    }

    /**
     * Gets first this month number.
     *
     * @return the first this month number
     */
    private Stats getFirstThisMonthNumber() {
        long  max  = 0;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getNumber() > max) {
                keep = s;
            }
        }
        return keep;
    }

    private Stats test(String id) {
        for (Map.Entry<String, Stats> s : map.entrySet()) {
            if (s.getKey().equals(id)) {
                return s.getValue();
            }
        }
        return null;
    }

    private void init(Map<String, Stats> allInfos) {
        this.map = allInfos;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("map", map)
                .toString();
    }
}
