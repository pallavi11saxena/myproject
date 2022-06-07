package org.example.servlet;

/*
 * Author @Pallavi Saxena
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.beans.SearchResponse;
import org.example.services.UUIDSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Servlet for search node on UUID API
 * http://localhost:8080/nodes/?uuid=
 */
@WebServlet(name = "UUIDSearch", urlPatterns = {"/uuid"})
public class UUIDSearchServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();
    private static Logger LOG = LoggerFactory.getLogger(UUIDSearchServlet.class);

    /**
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        LOG.debug("Entering doGet");
        try {
            UUIDSearchService uuidSearchService = new UUIDSearchService();
            SearchResponse searchResponse = uuidSearchService.searchNodeByUUID(req.getParameter("uuid"));

            res.setStatus(200);
            res.setHeader("Content-Type", "application/json");
            res.getOutputStream().println(GSON.toJson(searchResponse));

        } catch (RepositoryException e) {
            LOG.error(e.getMessage());
            res.setStatus(500);
            res.setHeader("Content-Type", "application/json");
        }
        LOG.debug("Exiting doGet");
    }
}
