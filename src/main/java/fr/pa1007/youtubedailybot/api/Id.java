
package fr.pa1007.youtubedailybot.api;

import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.Map;

public class Id {

    private String              kind;
    private String              videoId;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("kind", kind)
                .add("videoId", videoId)
                .add("additionalProperties", additionalProperties)
                .toString();
    }
}
