package fr.pa1007.youtubedailybot;

import fr.pa1007.youtubedailybot.api.API;
import fr.pa1007.youtubedailybot.api.Result;
import fr.pa1007.youtubedailybot.api.time.Time;
import fr.pa1007.youtubedailybot.statistics.Statistics;
import fr.pa1007.youtubedailybot.twitter.Twitter;
import fr.pa1007.youtubedailybot.youtube.Finder;
import fr.pa1007.youtubedailybot.youtube.Video;
import twitter4j.TwitterException;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class Main {


    private static int lastDay;

    /**
     * Get the time remaining between NOW and Tomorrow
     *
     * @return time in millisecond
     * @throws IOException    if the API doesn't work
     * @throws ParseException if the API doesn't work
     */
    private static long getTimeRemaining() throws IOException, ParseException {
        Date     today         = Time.getTime();
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(today);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int day = calendar.get(Calendar.DATE);
        if (calendartoday.get(Calendar.HOUR_OF_DAY) >= 12) {
            day++;
        }
        if (lastDay == day) {
            day++;
        }
        lastDay = day;
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                day,
                12,
                0,
                0
        );
        Date tomorrow = calendar.getTime();
        System.out.println(tomorrow);

        System.err.println(today);
        return tomorrow.getTime() - today.getTime();
    }

    /**
     * Main method, need refactor
     *
     * @param args the args
     */
    public static void main(String[] args) {
        Statistics s    = null;
        long       time = (long) 8.64e+7;
        try {
            s = new Statistics();
            time = getTimeRemaining();
        }
        catch (Exception e) {
            Mail.send(e, "ERROR");
        }
        sleep(time);

        while (true) {
            String twit = "ERROR";
            Video  f    = null;
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(Date.from(Instant.now()));
                c.add(Calendar.DATE, -1);
                Result te = API.getResult(c.getTime());

                try {
                    try {
                        f = Finder.getVideo(te);
                        s.addNew(f);
                        twit = Twitter.makeTweet(f);
                        System.out.println(twit);
                        Twitter.sendTweet(twit);
                    }
                    catch (IndexOutOfBoundsException e) {
                        Mail.send(new Exception("No video out"), twit);
                    }
                    if (c.get(Calendar.DAY_OF_MONTH) == c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                        String statTwit = Twitter.createStatTwit(s);
                        Twitter.sendTweet(statTwit);

                    }
                }
                catch (TwitterException e) {
                    if (f != null) {
                        try {
                            twit = Twitter.makeTweetWithoutTitle(f);
                        }
                        catch (Exception ex) {
                            Mail.send(ex, twit);
                        }
                    }
                    else {
                        Mail.send(e, twit);
                    }
                }
                time = getTimeRemaining();
                System.out.println("About to wait " + time + " near " + Date.from(Instant.ofEpochMilli(time)));
                Thread.sleep(time);
            }
            catch (InterruptedException e) {
                boolean b = Mail.send(e, twit);
                if (b) {
                    Thread.currentThread().interrupt();
                }
            }
            catch (Exception e) {
                Mail.send(e, twit);
            }
        }
    }


    private static void sleep(long time) {
        System.out.println("About to wait " + time + " near " + Date.from(Instant.ofEpochMilli(time)));
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            boolean b = Mail.send(e, "ERROR");
            if (b) {
                Thread.currentThread().interrupt();
            }
        }
    }


}

