
package org.example.restservices;
/*
 * Author @Pallavi Saxena
 */

import org.example.beans.SearchResponse;
import org.example.services.GetNodesService;
import org.example.services.UUIDSearchService;
import org.example.services.XPathSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/*
 * Service for rest apis
 * http://localhost:8080/nodes/
 */
@Produces({MediaType.APPLICATION_JSON})
@Path("/api/")
public class RestService extends org.hippoecm.hst.jaxrs.services.AbstractResource {

    private static Logger LOG = LoggerFactory.getLogger(RestService.class);

    @GET
    @Path("/node/")
    public SearchResponse getNodes(@Context HttpServletRequest servletRequest,
                                   @Context HttpServletResponse servletResponse,
                                   @Context UriInfo uriInfo) {

        LOG.debug("Entering getNodes");

        SearchResponse searchResponse = null;
        try {
            if (!servletRequest.getParameterMap().isEmpty() && servletRequest.getParameterMap().containsKey("search")) {
                XPathSearchService xPathSearchService = new XPathSearchService();
                searchResponse = xPathSearchService.searchNodeByXPath(servletRequest.getParameter("search").trim());
            } else if (!servletRequest.getParameterMap().isEmpty() && servletRequest.getParameterMap().containsKey("uuid")) {
                UUIDSearchService uuidSearchService = new UUIDSearchService();
                searchResponse = uuidSearchService.searchNodeByUUID(servletRequest.getParameter("uuid").trim());
            } else {
                GetNodesService getNodesService = new GetNodesService();
                searchResponse = getNodesService.getNodes();
            }
        } catch (RepositoryException e) {
            LOG.error(e.getMessage());
        }
        LOG.debug("Exiting getNodes");
        return searchResponse;
    }
}
