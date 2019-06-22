package fr.pa1007.youtubedailybot.twitter;

/**
 * The enum String message.
 */
public enum StringMessage {

    /**
     * Date string message.
     */
    DATE,
    /**
     * Next line string message.
     */
    NEXT_LINE,
    /**
     * Video name string message.
     */
    VIDEO_NAME,
    /**
     * Video creator string message.
     */
    VIDEO_CREATOR,
    /**
     * Video link string message.
     */
    VIDEO_LINK,
    /**
     * Video likes string message.
     */
    VIDEO_LIKES,
    /**
     * Video views string message.
     */
    VIDEO_VIEWS,
    /**
     * Stat video number string message.
     */
    STAT_VIDEO_NUMBER,
    /**
     * Stat total view string message.
     */
    STAT_TOTAL_VIEW,
    /**
     * Stat average view string message.
     */
    STAT_AVERAGE_VIEW,
    /**
     * Stat video channel first string message.
     */
    STAT_VIDEO_CHANNEL_FIRST,
    /**
     * Stat average like string message.
     */
    STAT_AVERAGE_LIKE,
    /**
     * Stat total like string message.
     */
    STAT_TOTAL_LIKE;


    /**
     * Return the type of the StringMessage
     *
     * @param s the possible case
     * @return the type; can throw an IllegalStateException if the markup is in the wrong form
     */
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
