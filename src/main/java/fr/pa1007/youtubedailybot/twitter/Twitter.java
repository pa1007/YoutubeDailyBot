package fr.pa1007.youtubedailybot.twitter;

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

    public static String makeTweet(Video video) {
        String        line;
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("tweet.txt"))) {
            while (br.ready()) {
                line = br.readLine();
                result.append(interpretLine(line, video));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void sendTweet(String line) {
        twitter4j.Twitter twitter = TwitterFactory.getSingleton();
        Status            status;
        try {
            status = twitter.updateStatus(line);
            System.out.println(status);
        }
        catch (TwitterException e) {
            e.printStackTrace();
        }
    }


    public static String interpretLine(String line, Video video) {
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
}
