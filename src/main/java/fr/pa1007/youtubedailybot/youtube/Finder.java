package fr.pa1007.youtubedailybot.youtube;

import fr.pa1007.youtubedailybot.api.Item;
import fr.pa1007.youtubedailybot.api.Result;

public class Finder {

    private Finder() {

    }

    public static Video getVideo(Result result) {
        Item   item  = result.getItems().get(0);
        String viID  = item.getId().getVideoId();
        String title = item.getSnippet().getTitle();
        String user  = item.getSnippet().getChannelTitle();
        return new Video(viID, user, title);
    }
}
