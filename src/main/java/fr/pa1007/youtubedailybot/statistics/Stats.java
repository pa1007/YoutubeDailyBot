package fr.pa1007.youtubedailybot.statistics;

import com.google.common.base.Objects;
import fr.pa1007.youtubedailybot.youtube.Video;

public class Stats {

    private String chanName;

    private String chanID;

    private long cumulatedViews;

    private long number;

    private long cumulatedLikes;

    private long cumulatedDisLikes;

    private long cumulatedComm;


    public Stats(Video f) {
        chanName = f.getCreator();
        chanID = f.getChanID();
        number = 1;
        cumulatedViews = f.getTrueViews();
        cumulatedLikes = f.getTruelikes();
        cumulatedComm = f.getTrueComm();
        cumulatedDisLikes = f.getTrueDislikes();

    }

    public Stats(
            String chanName,
            String chanID,
            long cumulatedViews,
            long number,
            long cumulatedLikes,
            long cumulatedDisLikes,
            long cumulatedComm
    ) {
        this.chanName = chanName;
        this.chanID = chanID;
        this.cumulatedViews = cumulatedViews;
        this.number = number;
        this.cumulatedLikes = cumulatedLikes;
        this.cumulatedDisLikes = cumulatedDisLikes;
        this.cumulatedComm = cumulatedComm;
    }

    public void add(Video f) {
        number++;
        cumulatedViews += f.getTrueViews();
        cumulatedLikes += f.getTruelikes();
        cumulatedComm += f.getTrueComm();
        cumulatedDisLikes += f.getTrueDislikes();
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

    public long getCumulatedComms() {
        return cumulatedComm;
    }

    public long getCumulatedDisLikes() {
        return cumulatedDisLikes;
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
