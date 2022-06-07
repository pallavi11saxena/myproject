package org.example.beans;

/*
 * Author @Pallavi Saxena
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/*
 * Data Object for Search Result
 */
@JsonRootName(value = "searchResult")
public class SearchResult {

    private String nodeName;
    private String nodePath;
    private List<String> properties;

    @JsonProperty(value = "nodeName")
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @param nodeName
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return properties
     */
    @JsonProperty(value = "properties")
    public List<String> getProperties() {
        return properties;
    }

    /**
     * @param properties
     */
    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    /**
     * @return nodePath
     */
    @JsonProperty(value = "nodePath")
    public String getNodePath() {
        return nodePath;
    }

    /**
     * @param nodePath
     */
    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }
}