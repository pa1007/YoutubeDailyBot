package fr.pa1007.youtubedailybot.twitter;

import fr.pa1007.youtubedailybot.statistics.Statistics;
import fr.pa1007.youtubedailybot.statistics.Stats;
import fr.pa1007.youtubedailybot.statistics.VideoException;
import fr.pa1007.youtubedailybot.youtube.Video;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyException;
import static fr.pa1007.youtubedailybot.twitter.StringMessage.VIDEO_NAME;

public class Twitter {

    /**
     * Remove constructor for statics method only
     */
    private Twitter() {
    }

    /**
     * Create the tweet for a video
     *
     * @param video the video about
     * @return a tweet from the tweet.txt file
     * @throws KeyException if the kay is not readable
     * @throws IOException  if the connection as an error
     */
    public static String makeTweet(Video video) throws IOException, KeyException {
        String         line;
        StringBuilder  result = new StringBuilder();
        BufferedReader br     = new BufferedReader(new FileReader("tweet.txt"));
        while (br.ready()) {
            line = br.readLine();
            result.append(interpretLine(line, video));
        }
        return result.toString();
    }

    /**
     * Send the tweet to tweeter
     *
     * @param line the tweet you want tho send
     * @throws TwitterException if the twitter API send an error
     */
    public static void sendTweet(String line) throws TwitterException {
        if (line.contains("&#39;")) {
            line = line.replace("&#39;", "'");
        }
        StatusUpdate      s       = new StatusUpdate(line);
        twitter4j.Twitter twitter = TwitterFactory.getSingleton();
        Status            status;
        status = twitter.updateStatus(s);
        System.out.println(status);

    }

    /**
     * Create the tweet for a Statistics
     *
     * @param statistics the statistics to find
     * @return The tweet formed from stat.txt
     * @throws IOException    if the stat.txt is unreadable
     * @throws VideoException if the program is unable to find a video
     */
    public static String createStatTwit(Statistics statistics) throws IOException, VideoException {
        StringBuilder  result = new StringBuilder();
        BufferedReader br     = new BufferedReader(new FileReader("stat.txt"));
        String         line   = br.readLine();
        System.out.println(line);
        System.out.println(statistics.getMap());
        Stats s = statistics.getFirstThisMonth(line);
        while (br.ready()) {
            line = br.readLine();
            result.append(interpretLineStat(line, s));
        }
        statistics.resetMonth();
        return result.toString();
    }

    public static String makeTweetWithoutTitle(Video video) throws IOException, KeyException {
        String         line;
        StringBuilder  result = new StringBuilder();
        BufferedReader br     = new BufferedReader(new FileReader("tweet.txt"));
        while (br.ready()) {
            line = br.readLine();
            if (line.startsWith("{") && StringMessage.getInstance(line) == VIDEO_NAME) {
                result.append("(Title too long)");
            }
            else {
                result.append(interpretLine(line, video));
            }
        }
        return result.toString();
    }

    /**
     * to understand markup (See readme.md)
     *
     * @param line  the line
     * @param video the video
     * @return an info
     * @throws KeyException if the key is unreadable
     * @throws IOException  if something is wrong
     */
    private static String interpretLine(String line, Video video) throws KeyException, IOException {
        if (line.startsWith("{")) {
            String result;
            switch (StringMessage.getInstance(line)) {
                case NEXT_LINE:
                    result = "\n";
                    break;
                case VIDEO_NAME:
                    result = video.getName();
                    break;
                case VIDEO_CREATOR:
                    result = video.getCreator();
                    break;
                case VIDEO_LINK:
                    result = video.getLink();
                    break;
                case VIDEO_LIKES:
                    result = video.getLikes();
                    break;
                case VIDEO_VIEWS:
                    result = video.getViews();
                    break;
                default:
                    result = line;
            }

            return result + " ";

        }
        return line + " ";
    }

    /**
     * to understand markup (See readme.md)
     *
     * @param line the line
     * @param s    the statistics
     * @return an info
     */
    private static String interpretLineStat(String line, Stats s) {
        if (line.startsWith("{")) {
            String result;
            switch (StringMessage.getInstance(line)) {
                case NEXT_LINE:
                    result = "\n";
                    break;
                case STAT_AVERAGE_VIEW:
                    result = String.valueOf(s.getAvgViews());
                    break;
                case STAT_VIDEO_NUMBER:
                    result = String.valueOf(s.getNumber());
                    break;
                case STAT_TOTAL_VIEW:
                    result = String.valueOf(s.getCumulatedViews());
                    break;
                case STAT_VIDEO_CHANNEL_FIRST:
                    result = s.getChanName();
                    break;
                case STAT_AVERAGE_LIKE:
                    result = String.valueOf(s.getAvgLikes());
                    break;
                case STAT_TOTAL_LIKE:
                    result = String.valueOf(s.getCumulatedLikes());
                    break;
                default:
                    result = line;
                    break;
            }
            return result + " ";
        }
        return line + " ";
    }
}
