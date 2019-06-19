package fr.pa1007.youtubedailybot.twitter;

public enum StringMessage {

    DATE,
    NEXT_LINE,
    VIDEO_NAME,
    VIDEO_CREATOR,
    VIDEO_LINK,
    VIDEO_LIKES,
    VIDEO_VIEWS,
    STAT_VIDEO_NUMBER,
    STAT_TOTAL_VIEW,
    STAT_AVERAGE_VIEW,
    STAT_VIDEO_CHANNEL_FIRST,
    STAT_AVERAGE_LIKE,
    STAT_TOTAL_LIKE;


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
            case "{DATE}":
                return DATE;
            case "{VIDEO_NUMBER}":
                return STAT_VIDEO_NUMBER;
            case "{TOTAL_VIEW}":
                return STAT_TOTAL_VIEW;
            case "{AVERAGE_VIEW}":
                return STAT_AVERAGE_VIEW;
            case "{VIDEO_CHANNEL_FIRST}":
                return STAT_VIDEO_CHANNEL_FIRST;
            case "{VIDEO_LIKE_AVG]":
                return STAT_AVERAGE_LIKE;
            case "{VIDEO_TOTAL_LIKE}":
                return STAT_TOTAL_LIKE;
            default:
                throw new IllegalStateException("Unexpected value: " + s);
        }
    }

}
