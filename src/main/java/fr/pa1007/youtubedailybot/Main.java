package fr.pa1007.youtubedailybot;

import fr.pa1007.youtubedailybot.api.API;
import fr.pa1007.youtubedailybot.api.Result;
import fr.pa1007.youtubedailybot.statistics.Statistics;
import fr.pa1007.youtubedailybot.twitter.Twitter;
import fr.pa1007.youtubedailybot.youtube.Finder;
import fr.pa1007.youtubedailybot.youtube.Video;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Statistics s     = new Statistics();
        boolean    canDo = false;
        while (true) {
            if (!canDo) {
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
                        s.addNew(f);
                    }
                    catch (IndexOutOfBoundsException e) {
                        Mail.send(new Exception("No video out"));
                    }
                    if (c.get(Calendar.DAY_OF_MONTH) == c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                        String statTwit = Twitter.createStatTwit(s);
                        Twitter.sendTweet(statTwit);

                    }

                    Thread.sleep((long) 8.64e+7);
                }
                catch (InterruptedException e) {
                    boolean b = Mail.send(e);
                    if (b) {
                        Thread.currentThread().interrupt();
                    }
                    canDo = true;
                }
                catch (Exception e) {
                    Mail.send(e);
                    canDo = true;
                }
            }
            else {
                Scanner scanner = new Scanner(System.in);
                System.err.println("Waiting input to resume : .....");
                String string = scanner.nextLine();
                if (string.equals("Stop")) {
                    System.out.println(s);
                    break;
                }
                else {
                    canDo = false;
                }
            }
        }
    }


}

