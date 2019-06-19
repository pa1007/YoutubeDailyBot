
package fr.pa1007.youtubedailybot.api;

import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.Map;

public class High {

    private String              url;
    private int                 width;
    private int                 height;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
                .add("url", url)
                .add("width", width)
                .add("height", height)
                .add("additionalProperties", additionalProperties)
                .toString();
    }
}
