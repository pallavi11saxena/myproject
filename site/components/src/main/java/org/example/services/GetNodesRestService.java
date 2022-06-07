package org.example.services;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.site.HstServices;
import org.hippoecm.hst.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

@Path("/api")
public class GetNodesRestService extends org.hippoecm.hst.jaxrs.services.AbstractResource {

    static Set<String> response = new TreeSet<String>();
    private static Logger log = LoggerFactory.getLogger(GetNodesRestService.class);

    @GET
    @Path("/")
    public Set<String> getProductResources(
            @Context HttpServletRequest servletRequest,
            @Context HttpServletResponse servletResponse,
            @Context UriInfo uriInfo) throws RepositoryException {

        Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
        Session session = null;
        try {
            HstRequestContext requestContext = RequestContextProvider.get();
            String mountContentPath =
                    requestContext.getResolvedMount().getMount()
                            .getContentPath();
            Node mountContentNode =
                    requestContext.getSession().getRootNode()
                            .getNode(PathUtils.normalizePath(mountContentPath));
            session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

            Node rootNode = session.getRootNode();

            NodeIterator childrenOfRoot = rootNode.getNodes();
            while (childrenOfRoot.hasNext()) {
                Node currentChildOfRoot = childrenOfRoot.nextNode();
                if (currentChildOfRoot.getName().equals("content")) {
                    NodeIterator childrenOfContent = currentChildOfRoot.getNodes();
                    while (childrenOfContent.hasNext()) {
                        Node currentChildOfContent = childrenOfContent.nextNode();
                        if (currentChildOfContent.getName().equals("documents")) {
                            response.addAll(iterateNodes(currentChildOfContent, response));
                        }

                    }
                }
            }

            response.forEach((i) -> {
                log.info(i);
                try {
                    servletResponse.getWriter().write(i + "\n");
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            });
        } catch (RepositoryException e) {
            e.printStackTrace();
            log.info(e.getMessage());
// do something on errors
        } finally {
// do something if needed.
        }
        System.out.println("output: " + response);
        return response;
    }

    private Set<String> iterateNodes(Node node, Set<String> res) throws RepositoryException {

        NodeIterator nodeList = node.getNodes();

        while (nodeList.hasNext()) {
            Node currentnode = nodeList.nextNode();

            res.add(currentnode.getPath());

            if (currentnode.getNodes() != null && !currentnode.isNodeType("hippofacnav:facetnavigation")) {
                iterateNodes(currentnode, res);
            }
        }
        return res;
    }

}

