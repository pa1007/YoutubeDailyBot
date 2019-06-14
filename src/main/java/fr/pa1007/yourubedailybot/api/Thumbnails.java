
package fr.pa1007.yourubedailybot.api;

import java.util.HashMap;
import java.util.Map;

public class Thumbnails {

    private Default             _default;
    private Medium              medium;
    private High                high;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

}
