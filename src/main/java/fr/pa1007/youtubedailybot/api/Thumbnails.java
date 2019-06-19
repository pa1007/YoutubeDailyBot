
package fr.pa1007.youtubedailybot.api;

import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.Map;

public class Thumbnails {

    private Default             _default;
    private Medium              medium;
    private High                high;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Default getDefault() {
        return _default;
    }

    public void setDefault(Default _default) {
        this._default = _default;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
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
                .add("_default", _default)
                .add("medium", medium)
                .add("high", high)
                .add("additionalProperties", additionalProperties)
                .toString();
    }
}
