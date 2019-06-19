package fr.pa1007.youtubedailybot.twitter;

public enum StringMessage {

    NEXT_LINE("{NEXT_LINE}"),
    VIDEO_NAME("{VIDEO_NAME}"),
    VIDEO_CREATOR("{VIDEO_CHANNEL}"),
    VIDEO_LINK("{VIDEO_LINK}"),
    VIDEO_LIKES("{VIDEO_LIKES_NUMBER}"),
    VIDEO_VIEWS("{VIDEO_VIEWS}");


    private String text;


    StringMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static StringMessage getInstance(String s) {
        switch (s) {
            case "{NEXT_LINE}":
                return NEXT_LINE;
            case "{VIDEO_NAME}":
                return VIDEO_NAME;
            case "{VIDEO_CHANNEL}":
                return VIDEO_CREATOR;
            case "{VIDEO_LINK}":
                return VIDEO_LINK;
            case "{VIDEO_LIKES_NUMBER}":
                return VIDEO_LIKES;
            case "{VIDEO_VIEWS}":
                return VIDEO_VIEWS;
            default:
                throw new IllegalStateException("Unexpected value: " + s);
        }
    }

}
