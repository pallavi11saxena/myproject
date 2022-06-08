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
import java.util.ArrayList;
import java.util.List;

/*
 * Service to get nodes list
 * http://localhost:8080/nodes/
 */
public class GetNodesService {

    private static Logger LOG = LoggerFactory.getLogger(GetNodesService.class);

    /**
     * @return SearchResponse
     * @throws RepositoryException
     */
    public SearchResponse getNodes() throws RepositoryException {
        {
            LOG.debug("Entering getNodes");
            Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
            Session session = null;

            SearchResponse searchResponse = new SearchResponse();
            searchResponse.setSearchResults(new ArrayList<SearchResult>());

            try {
                session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

                Node rootNode = session.getRootNode();

                // recursively iterate nodes to list all nodes under content/documents node
                NodeIterator childrenOfRoot = rootNode.getNodes();
                while (childrenOfRoot.hasNext()) {
                    Node currentChildOfRoot = childrenOfRoot.nextNode();
                    if (currentChildOfRoot.getName().equals("content")) {
                        NodeIterator childrenOfContent = currentChildOfRoot.getNodes();
                        while (childrenOfContent.hasNext()) {
                            Node currentChildOfContent = childrenOfContent.nextNode();
                            if (currentChildOfContent.getName().equals("documents")) {
                                iterateNodes(currentChildOfContent, searchResponse.getSearchResults());
                            }
                        }
                    }
                }

            } catch (RepositoryException e) {
                LOG.info(e.getMessage());
                throw e;
            } finally {
                session.logout();
            }
            LOG.debug("Exiting getNodes");
            return searchResponse;
        }
    }

    /**
     * Recursively iterates over current node to set node name and path in the response object list
     *
     * @param node, current node
     * @param res,  SearchResponse.SearchResults
     * @return SearchResponse.SearchResults
     * @throws RepositoryException
     */
    private List<SearchResult> iterateNodes(Node node, List<SearchResult> res) throws RepositoryException {
        LOG.debug("Entering iterateNodes");

        NodeIterator nodeList = node.getNodes();

        while (nodeList.hasNext()) {
            Node currentnode = nodeList.nextNode();

            SearchResult searchResult = new SearchResult();
            searchResult.setNodeName(currentnode.getName());
            searchResult.setNodePath(currentnode.getPath());
            res.add(searchResult);

            if (currentnode.getNodes() != null && !currentnode.isNodeType("hippofacnav:facetnavigation")) {
                iterateNodes(currentnode, res);
            }
        }
        LOG.debug("Exiting iterateNodes");
        return res;
    }
}
