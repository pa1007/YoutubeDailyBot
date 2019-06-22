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


    private static long getTimeRemaining() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE) + 1,
                12,
                0,
                0
        );
        Date tomorrow = calendar.getTime();
        System.out.println(tomorrow);
        return tomorrow.getTime() - Date.from(Instant.now()).getTime();
    }

    public static void main(String[] args) {
        System.out.println(getTimeRemaining());
        boolean    canDo = false;
        Statistics s     = null;
        try {
            s = new Statistics();
        }
        catch (Exception e) {
            Mail.send(e);
            canDo = true;
        }
        long time = getTimeRemaining();
        System.out.println("About to wait " + time + " near " + Date.from(Instant.ofEpochMilli(time)));
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            boolean b = Mail.send(e);
            if (b) {
                Thread.currentThread().interrupt();
            }
            canDo = true;
        }

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

                    Thread.sleep(getTimeRemaining());
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

