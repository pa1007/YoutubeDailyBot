package fr.pa1007.youtubedailybot.statistics;

import com.google.common.base.Objects;
import fr.pa1007.youtubedailybot.youtube.Video;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public final class Statistics {

    private Map<String, Stats>              map;
    private Map<String, Map<String, Stats>> monthMap;

    public Statistics() {
        map = new HashMap<>();
        monthMap = new HashMap<>();
    }

    public void addNew(Video f) {
        Stats s = test(f.getChanID());
        if (s == null) {
            map.put(f.getChanID(), new Stats(f));
        }
        else {
            s.add(f);
        }
    }


    public Stats test(String id) {
        for (Map.Entry<String, Stats> s : map.entrySet()) {
            if (s.getKey().equals(id)) {
                return s.getValue();
            }
        }
        return null;
    }

    public Map<String, Stats> getMap() {
        return map;
    }

    public Stats getFirstThisMonthViews() {
        long  max  = Long.MIN_VALUE;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getCumulatedViews() > max) {
                keep = s;
            }
        }
        return keep;
    }

    public Stats getFirstThisMonthAVGView() {
        long  max  = Long.MIN_VALUE;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getAvgViews() > max) {
                keep = s;
            }
        }
        return keep;
    }

    public Stats getFirstThisMonthAVGLikes() {
        long  max  = Long.MIN_VALUE;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getAvgLikes() > max) {
                keep = s;
            }
        }
        return keep;
    }

    public Stats getFirstThisMonthLikes() {
        long  max  = Long.MIN_VALUE;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getCumulatedLikes() > max) {
                keep = s;
            }
        }
        return keep;
    }

    public Stats getFirstThisMonthNumber() {
        long  max  = 0;
        Stats keep = null;
        for (Stats s : map.values()) {
            if (s.getNumber() > max) {
                keep = s;
            }
        }
        return keep;
    }

    public void resetMonth() {
        String[] monthName = DateFormatSymbols.getInstance().getMonths();
        Calendar cal       = Calendar.getInstance();
        String   month     = monthName[cal.get(Calendar.MONTH)];

        monthMap.put(month, new HashMap<>(map));
        map.clear();
    }

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
            case "FIRST LINE : NUMBER":
                s = getFirstThisMonthNumber();
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("map", map)
                .toString();
    }
}