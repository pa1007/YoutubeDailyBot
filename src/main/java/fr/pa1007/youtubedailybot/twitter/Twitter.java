package fr.pa1007.youtubedailybot.twitter;

import fr.pa1007.youtubedailybot.statistics.Statistics;
import fr.pa1007.youtubedailybot.statistics.Stats;
import fr.pa1007.youtubedailybot.statistics.VideoException;
import fr.pa1007.youtubedailybot.youtube.Video;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Twitter {

    private Twitter() {
    }

    public static String makeTweet(Video video) throws IOException {
        String         line;
        StringBuilder  result = new StringBuilder();
        BufferedReader br     = new BufferedReader(new FileReader("tweet.txt"));
        while (br.ready()) {
            line = br.readLine();
            result.append(interpretLine(line, video));
        }
        return result.toString();
    }

    public static void sendTweet(String line) throws TwitterException {
        twitter4j.Twitter twitter = TwitterFactory.getSingleton();
        Status            status;
        status = twitter.updateStatus(line);
        System.out.println(status);

    }

    public static String createStatTwit(Statistics statistics) throws IOException, VideoException {
        StringBuilder  result = new StringBuilder();
        BufferedReader br     = new BufferedReader(new FileReader("stat.txt"));
        String         line;
        Stats          s      = statistics.getFirstThisMonth(br.readLine());
        while (br.ready()) {
            line = br.readLine();
            result.append(interpretLineStat(line, s));
        }
        statistics.resetMonth();
        return result.toString();
    }

    private static String interpretLine(String line, Video video) {
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
