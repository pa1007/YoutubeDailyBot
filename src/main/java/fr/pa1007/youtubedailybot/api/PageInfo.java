
package fr.pa1007.youtubedailybot.api;

import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.Map;

public class PageInfo {

    private Integer             totalResults;
    private Integer             resultsPerPage;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
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
                .add("totalResults", totalResults)
                .add("resultsPerPage", resultsPerPage)
                .add("additionalProperties", additionalProperties)
                .toString();
    }
}
