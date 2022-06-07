/*
 * Author @Pallavi Saxena
 */

package org.example.services;

import org.example.beans.SearchResponse;
import org.example.beans.SearchResult;
import org.hippoecm.hst.core.container.ComponentManager;
import org.hippoecm.hst.site.HstServices;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.jcr.*;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.ActivityViolationException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

@RunWith(PowerMockRunner.class)
public class GetNodesServiceTest {


   @Mock
   HstServices hstServices;

    @Test
    public void getNodes() throws RepositoryException {
        GetNodesService service = new GetNodesService();

        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setSearchResults(new ArrayList<SearchResult>());
        SearchResult searchResult = new SearchResult();
        searchResult.setNodeName("myproject");
        searchResult.setNodePath("/content/documents/myproject");
        searchResponse.getSearchResults().add(searchResult);


        SearchResponse response = service.getNodes();
        Assertions.assertEquals(searchResponse.getSearchResults().get(0), response.getSearchResults().get(0));
    }
}
