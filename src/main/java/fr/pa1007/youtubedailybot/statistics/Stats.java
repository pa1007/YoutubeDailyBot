package fr.pa1007.youtubedailybot.statistics;

import com.google.common.base.Objects;
import fr.pa1007.youtubedailybot.youtube.Video;

public class Stats {

    private String chanName;

    private String chanID;

    private long cumulatedViews;

    private long number;

    private long cumulatedLikes;


    public Stats(Video f) {
        chanName = f.getCreator();
        chanID = f.getChanID();
        number = 1;
        cumulatedViews = f.getTruelikes();
        cumulatedLikes = f.getTruelikes();

    }

    public void add(Video f) {
        number++;
        cumulatedViews += f.getTruelikes();
        cumulatedLikes += f.getTruelikes();
    }

    public long getAvgViews() {
        return cumulatedViews / number;
    }

    public String getChanName() {
        return chanName;
    }

    public String getChanID() {
        return chanID;
    }

    public long getAvgLikes() {
        return cumulatedLikes / number;
    }

    public long getCumulatedViews() {
        return cumulatedViews;
    }

    public long getNumber() {
        return number;
    }

    public long getCumulatedLikes() {
        return cumulatedLikes;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("chanName", chanName)
                .add("chanID", chanID)
                .add("cumulatedViews", cumulatedViews)
                .add("number", number)
                .add("cumulatedLikes", cumulatedLikes)
                .toString();
    }
}
