package org.example.services;

/*
 * Author @Pallavi Saxena
 */

import org.example.beans.SearchResponse;
import org.example.beans.SearchResult;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.util.ArrayList;

/*
 * Service to get nodes using search string
 * http://localhost:8080/nodes?search=
 */
public class XPathSearchService {

    private static Logger LOG = LoggerFactory.getLogger(XPathSearchService.class);

    /**
     * @return SearchResponse
     * @throws RepositoryException
     */
    public SearchResponse searchNodeByXPath(String queryStringInput) throws RepositoryException {
        {
            LOG.debug("Entering searchNodeByXPath");
            Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
            Session session = null;

            SearchResponse searchResponse = new SearchResponse();
            searchResponse.setSearchResults(new ArrayList<SearchResult>());

            try {
                session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

                String queryString = queryStringInput;
                QueryManager queryManager = session.getWorkspace().getQueryManager();
                Query query = queryManager.createQuery(queryString, "xpath");
                QueryResult queryResult = query.execute();

                NodeIterator nodeList = queryResult.getNodes();

                while (nodeList.hasNext()) {
                    Node currentNode = nodeList.nextNode();
                    SearchResult searchResult = new SearchResult();
                    searchResult.setProperties(new ArrayList<String>());

                    searchResult.setNodeName(currentNode.getName());
                    searchResult.setNodePath(currentNode.getPath());
                    LOG.debug("Node: " + currentNode.getName());

                    PropertyIterator propertyList = currentNode.getProperties();

                    while (propertyList.hasNext()) {
                        Property currentProp = propertyList.nextProperty();
                        searchResult.getProperties().add(currentProp.getName());
                        LOG.debug("Property:" + currentProp.getName());
                    }
                    searchResponse.getSearchResults().add(searchResult);
                }

            } catch (RepositoryException e) {
                LOG.error(e.getMessage());
                throw e;
            } finally {
                session.logout();
            }
            LOG.debug("Exiting searchNodeByXPath");
            return searchResponse;
        }
    }
}
