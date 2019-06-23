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

/**
 * The type Video.
 */
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

    /**
     * @param viID   the id of the video
     * @param user   the user who has done this video
     * @param title  the title of the video
     * @param chanID the id of the channel
     */
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

    @Deprecated(forRemoval = true)
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

    /**
     * Gets true views.
     *
     * @return the true views
     */
    public long getTrueViews() {
        return views;
    }

    /**
     * Gets true likes.
     *
     * @return the true likes
     */
    public long getTrueLikes() {
        return likes;
    }

    /**
     * @return the name of the video between <code>"</code>
     */
    public String getName() {
        return "\"" + title + "\"";
    }

    /**
     * @return the creator of the video
     */
    public String getCreator() {
        return user;
    }

    /**
     * @return the link of the video
     */
    public String getLink() {
        return "https://www.youtube.com/watch?v=" + videoID;
    }

    /**
     * Gets likes.
     *
     * @return the dislike
     * @throws KeyException if the kay is not readable
     * @throws IOException  if the connection as an error
     */
    public String getLikes() throws KeyException, IOException {
        if (researchDone) {
            return like;
        }
        else {
            makeResearch();
            return like;
        }
    }

    /**
     * Gets dislike.
     *
     * @return the dislike
     * @throws KeyException if the kay is not readable
     * @throws IOException  if the connection as an error
     */
    public String getDislike() throws KeyException, IOException {
        if (researchDone) {
            return dislike;
        }
        else {
            makeResearch();
            return dislike;
        }
    }

    /**
     * Gets views.
     *
     * @return the views
     * @throws KeyException if the kay is not readable
     * @throws IOException  if the connection as an error
     */
    public String getViews() throws KeyException, IOException {
        if (researchDone) {
            return viewNb;
        }
        else {
            makeResearch();
            return viewNb;
        }
    }

    /**
     * Gets chan id.
     *
     * @return the chan id
     */
    public String getChanID() {
        return chanID;
    }

    /**
     * Gets true comment number.
     *
     * @return the true comm
     */
    public long getTrueComm() {
        return comms;
    }

    /**
     * Gets true dislikes number.
     *
     * @return the true dislikes
     */
    public long getTrueDislikes() {
        return dislikes;
    }

    /**
     * This will make a research with Youtube API for getting all the infos in
     *
     * @throws KeyException if the kay is not readable
     * @throws IOException  if the connection as an error
     */
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
        if (vid.getCommentCount() != null) {
            comms = Long.parseLong(vid.getCommentCount());
        }
        comm = prettyNumber(Math.toIntExact(comms));
    }

    /**
     * For formatting a number example :  982658 to 982.65K or 10120000 to 10.12M
     *
     * @param num the number to change
     * @return the "Cute" number
     */
    private static String prettyNumber(int num) {
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

    /**
     * Give the unit for the number of time it has gone through
     *
     * @param count the number of time
     * @return A String (K,M,B,T or "")
     */
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
