package org.example.beans;
/*
 * Author @Pallavi Saxena
 */

import java.util.List;

/*
 * Data Object for API response
 */
public class SearchResponse {

    private List<SearchResult> searchResults;

    /**
     * @return searchResults
     */
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
