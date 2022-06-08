package org.example.servlet;

/*
 * Author @Pallavi Saxena
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.beans.SearchResponse;
import org.example.services.GetNodesService;
import org.example.services.UUIDSearchService;
import org.example.services.XPathSearchService;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Servlet to filter search requests (uuid or XPath)
 * http://localhost:8080/nodes/
 */
@WebServlet(name = "Assessment", urlPatterns = {"/nodes"})
public class AssessmentServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();
    private static Logger LOG = LoggerFactory.getLogger(AssessmentServlet.class);

    /**
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        LOG.debug("Entering doGet");
        Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
        Session session = null;

        SearchResponse searchResponse = null;

        try {
            session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

            //call XPathSearchService if the request contains 'search' parameter
            if (!req.getParameterMap().isEmpty() && req.getParameterMap().containsKey("search")) {
                XPathSearchService xPathSearchService = new XPathSearchService();
                searchResponse = xPathSearchService.searchNodeByXPath(req.getParameter("search").trim());
            }
            //call XPathSearchService if the request contains 'uuid' parameter
            else if (!req.getParameterMap().isEmpty() && req.getParameterMap().containsKey("uuid")) {
                UUIDSearchService uuidSearchService = new UUIDSearchService();
                searchResponse = uuidSearchService.searchNodeByUUID(req.getParameter("uuid"));
            }
            //call GetNodesService if the request doe not contain any parameter
            else {
                GetNodesService getNodesService = new GetNodesService();
                searchResponse = getNodesService.getNodes();
            }

            res.setStatus(200);
            res.setHeader("Content-Type", "application/json");
            res.getOutputStream().println(GSON.toJson(searchResponse));

        } catch (RepositoryException e) {
            LOG.info(e.getMessage());
            res.setStatus(500);
            res.setHeader("Content-Type", "application/json");
        } finally {
            session.logout();
        }
        LOG.debug("Exiting doGet");
    }
}
