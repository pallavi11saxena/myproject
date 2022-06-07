package org.example.beans;
/*
 * Author @Pallavi Saxena
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/*
 * Data Object for API response
 */
@JsonRootName(value = "searchResponse")
public class SearchResponse {

    private List<SearchResult> searchResults;

    /**
     * @return searchResults
     */
    @JsonProperty(value = "searchResults")
    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    /**
     * @param searchResults
     */
    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
