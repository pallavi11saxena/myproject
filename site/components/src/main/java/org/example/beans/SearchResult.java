package org.example.beans;

/*
 * Author @Pallavi Saxena
 */

import java.util.List;

/*
 * Data Object for Search Result
 */
public class SearchResult {

    private String nodeName;
    private String nodePath;
    private List<String> properties;

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