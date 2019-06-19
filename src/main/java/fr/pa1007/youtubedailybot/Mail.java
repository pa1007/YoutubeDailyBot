package fr.pa1007.youtubedailybot;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mail {

    private static List<String> mailToSend = new ArrayList<>();


    public static boolean send(Exception e) {
        String s = createString(e);
        try {
            twitter4j.Twitter twitter = TwitterFactory.getSingleton();
            twitter.sendDirectMessage("Yoloteist", s);
            for (Iterator<String> iterator = mailToSend.iterator(); iterator.hasNext(); ) {
                twitter.directMessages().sendDirectMessage("Yoloteist", iterator.next());
                iterator.remove();
            }
        }
        catch (TwitterException ex) {
            mailToSend.add(s);
            return false;
        }
        return true;

    }

    public static String createString(Exception e) {
        StringBuilder r = new StringBuilder(Date.from(Instant.now()) + " ;"
                                            +
                                            "Dear pa1007, My creator, i'm here because of the Application Daily Youtube video, something went wrong and you will have the exception; \n"
                                            + "Please remind that if the exception is an InterruptedException you will need to restart it manually, it might be important to look after each exception \n"
                                            + "I think you can find the error my friend, please do something quick ! \n\n\n"
                                            + ""
                                            + "Best regard pa1007\n\n\n");
        r.append(e.getLocalizedMessage()).append("\n");
        r.append(e.getClass()).append("\n");
        for (StackTraceElement st : e.getStackTrace()) {
            r.append(st).append("\n");
        }
        return r.toString();
    }
}
