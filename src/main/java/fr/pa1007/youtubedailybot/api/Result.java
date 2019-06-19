
package fr.pa1007.youtubedailybot.api;

import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Result {

    private String              kind;
    private String              etag;
    private String              nextPageToken;
    private String              regionCode;
    private PageInfo            pageInfo;
    private List<Item>          items                = null;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
                .add("etag", etag)
                .add("nextPageToken", nextPageToken)
                .add("regionCode", regionCode)
                .add("pageInfo", pageInfo)
                .add("items", items)
                .add("additionalProperties", additionalProperties)
                .toString();
    }
}
