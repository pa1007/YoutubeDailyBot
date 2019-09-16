package fr.pa1007.youtubedailybot;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mail {

    /**
     * The tweeter account name you want to send the message
     */
    private static final String       ACCOUNT    = ""; // TODO ACCOUNT
    /**
     * List of mail who hasn't been send
     */
    private static       List<String> mailToSend = new ArrayList<>();

    private Mail() {

    }

    /**
     * Send the message associated to this Exception, if the message is send, it will check for the potentials one in the list
     *
     * @param e    the exception
     * @param twit
     * @return true if send, false if not
     */
    public static boolean send(Exception e, String twit) {
        String s = createString(e, twit);
        try {
            twitter4j.Twitter twitter = TwitterFactory.getSingleton();
            twitter.sendDirectMessage(ACCOUNT, s);
            for (Iterator<String> iterator = mailToSend.iterator(); iterator.hasNext(); ) {
                twitter.directMessages().sendDirectMessage(ACCOUNT, iterator.next());
                iterator.remove();
            }
        }
        catch (TwitterException ex) {
            mailToSend.add(s);
            return false;
        }
        return true;

    }

    /**
     * To create the message from the exception
     *
     * @param e    the exception
     * @param twit le message a envoyer
     * @return The message
     */
    public static String createString(Exception e, String twit) {
        StringBuilder r = new StringBuilder(Date.from(Instant.now()) + " ;");
        //ADD A MESSAGE
        r.append("tweet : ").append(twit).append("\n");
        r.append(e.getLocalizedMessage()).append("\n");
        r.append(e.getClass()).append("\n");
        for (StackTraceElement st : e.getStackTrace()) {
            r.append(st).append("\n");
        }
        return r.toString();
    }
}
