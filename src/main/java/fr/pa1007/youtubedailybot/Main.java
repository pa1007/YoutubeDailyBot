package fr.pa1007.youtubedailybot;

import fr.pa1007.youtubedailybot.api.API;
import fr.pa1007.youtubedailybot.api.Result;
import fr.pa1007.youtubedailybot.twitter.Twitter;
import fr.pa1007.youtubedailybot.youtube.Finder;
import fr.pa1007.youtubedailybot.youtube.Video;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class Main {


    public static void main(String[] args) {
        while (true) {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(Date.from(Instant.now()));
                c.add(Calendar.DATE, -1);
                Result te = API.getResult(c.getTime());
                Video  f;
                try {
                    f = Finder.getVideo(te);
                    String twit = Twitter.makeTweet(f);
                    System.out.println(twit);
                    Twitter.sendTweet(twit);
                }
                catch (IndexOutOfBoundsException e) {
                    Mail.send(new Exception("No video out"));
                }
                Thread.sleep((long) 8.64e+7);
            }
            catch (InterruptedException e) {
                boolean b = Mail.send(e);
                if (b) {
                    Thread.currentThread().interrupt();
                }
            }
            catch (Exception e) {
                Mail.send(e);
            }
        }
    }


}

