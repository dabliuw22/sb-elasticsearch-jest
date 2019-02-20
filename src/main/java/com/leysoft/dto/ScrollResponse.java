
package com.leysoft.dto;

import java.util.List;

public class ScrollResponse<T> {

    private String scrollId;

    private List<T> results;

    public ScrollResponse(String scrollId, List<T> results) {
        this.scrollId = scrollId;
        this.results = results;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ScrollResponse [scrollId=" + scrollId + ", results=" + results + "]";
    }
}
