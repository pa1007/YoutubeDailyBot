package fr.pa1007.youtubedailybot.youtube;

import fr.pa1007.youtubedailybot.api.Item;
import fr.pa1007.youtubedailybot.api.Result;

public class Finder {

    /**
     * Only static method
     */
    private Finder() {

    }

    /**
     * To get the first video from the result
     *
     * @param result the result found
     * @return A video
     * @see Video
     */
    public static Video getVideo(Result result) {
        Item   item   = result.getItems().get(0);
        String chanID = item.getSnippet().getChannelId();
        String viID   = item.getId().getVideoId();
        String title  = item.getSnippet().getTitle();
        String user   = item.getSnippet().getChannelTitle();
        return new Video(viID, user, title, chanID);
    }
}
