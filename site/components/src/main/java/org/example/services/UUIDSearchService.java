package org.example.services;

/*
 * Author @Pallavi Saxena
 */

import org.example.beans.SearchResponse;
import org.example.beans.SearchResult;
import org.example.servlet.GetNodesServlet;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.query.QueryManager;
import java.util.ArrayList;
import java.util.List;

/*
 * Servlet to get nodes list
 * http://localhost:8080/nodes/
 */
public class UUIDSearchService {

    private static Logger LOG = LoggerFactory.getLogger(GetNodesServlet.class);

    /**
     * @return SearchResponse
     * @throws RepositoryException
     */
    public SearchResponse searchNodeByUUID(String uuid) throws RepositoryException {
        {
            LOG.debug("Entering searchNodeByUUID");
            Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
            Session session = null;

            SearchResponse searchResponse = new SearchResponse();
            searchResponse.setSearchResults(new ArrayList<SearchResult>());

            try {
                session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

                QueryManager queryManager = session.getWorkspace().getQueryManager();

                Node node = session.getNodeByIdentifier(uuid);

                SearchResult searchResult = new SearchResult();
                searchResult.setProperties(new ArrayList<String>());
                searchResult.setNodeName(node.getName());
                searchResult.setNodePath(node.getPath());

                PropertyIterator propertyList = node.getProperties();
                while (propertyList.hasNext()) {
                    Property currentProp = propertyList.nextProperty();
                    searchResult.getProperties().add(currentProp.getName());
                    LOG.info("Property:" + currentProp.getName());
                }

                searchResponse.getSearchResults().add(searchResult);

            } catch (RepositoryException e) {
                LOG.info(e.getMessage());
                throw e;
            } finally {
                session.logout();
            }
            LOG.debug("Exiting searchNodeByUUID");
            return searchResponse;
        }
    }
}
