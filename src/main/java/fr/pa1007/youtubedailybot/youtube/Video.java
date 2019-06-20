package fr.pa1007.youtubedailybot.youtube;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fr.pa1007.youtubedailybot.api.API;
import fr.pa1007.youtubedailybot.api.video.Statistics;
import fr.pa1007.youtubedailybot.api.video.VideoAPI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.net.URL;
import java.security.KeyException;
import java.text.DecimalFormat;

public class Video {

    static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private final String title;

    private final String user;

    private final String videoID;

    private final String chanID;
    private       String viewNb;

    private long views;
    private long likes;
    private long dislikes;
    private long comms;

    private boolean researchDone;
    private String  dislike;
    private String  comm;
    private String  like;

    public Video(String viID, String user, String title, String chanID) {
        this.videoID = viID;
        this.user = user;
        this.title = title;
        this.chanID = chanID;
        try {
            makeResearch();
            researchDone = true;
        }
        catch (KeyException | IOException e) {
            researchDone = false;
        }
    }

    public Video(String viID, String user, String title, String chanID, String viewNb, String dislike, String like) {
        this.videoID = viID;
        this.title = title;
        this.user = user;
        this.chanID = chanID;
        this.viewNb = viewNb;
        this.dislike = dislike;
        this.like = like;
        researchDone = true;
    }

    public long getTrueViews() {
        return views;
    }

    public long getTruelikes() {
        return likes;
    }

    public String getName() {
        return "\"" + title + "\"";
    }

    public String getCreator() {
        return user;
    }

    public String getLink() {
        return "https://www.youtube.com/watch?v=" + videoID;
    }

    public String getLikes() throws KeyException, IOException {
        if (researchDone) {
            return like;
        }
        else {
            makeResearch();
            return like;
        }
    }

    public String getDislike() throws KeyException, IOException {
        if (researchDone) {
            return dislike;
        }
        else {
            makeResearch();
            return dislike;
        }
    }

    public String getViews() throws KeyException, IOException {
        if (researchDone) {
            return viewNb;
        }
        else {
            makeResearch();
            return viewNb;
        }
    }

    public String getChanID() {
        return chanID;
    }

    public long getTrueComm() {
        return comms;
    }

    public long getTrueDislikes() {
        return dislikes;
    }

    private void makeResearch() throws KeyException, IOException {
        String url =
                "https://www.googleapis.com/youtube/v3/videos?part=statistics&id=" + videoID + "&key=" + API.getKey();
        Type     clazzListType = new TypeToken<VideoAPI>() {}.getType();
        VideoAPI videoAPI      = null;
        try (Reader data = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream()))) {
            videoAPI = GSON.fromJson(data, clazzListType);
        }
        Statistics vid = videoAPI.getItems().get(0).getStatistics();
        views = Long.parseLong(vid.getViewCount());
        viewNb = prettyNumber(Math.toIntExact(views));
        likes = Long.parseLong(vid.getLikeCount());
        like = prettyNumber(Math.toIntExact(likes));
        dislikes = Long.parseLong(vid.getDislikeCount());
        dislike = prettyNumber(Math.toIntExact(dislikes));
        comms = Long.parseLong(vid.getCommentCount());
        comm = prettyNumber(Math.toIntExact(comms));
    }

    public static String prettyNumber(int num) {
        DecimalFormat f = new DecimalFormat("###.##");
        f.setRoundingMode(RoundingMode.HALF_UP);
        double prettyd = num;
        int    count   = 0;
        while (prettyd >= 1000.0) {
            prettyd /= 1000.0;
            count++;
        }
        return f.format(prettyd) + getUnit(count);
    }

    private static String getUnit(int count) {
        switch (count) {
            case 1:
                return "K";
            case 2:
                return "M";
            case 3:
                return "B";
            case 4:
                return "T";
            default:
                return "";
        }
    }
}
