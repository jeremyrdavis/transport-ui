package io.arrogantprogrammer.rest;

import io.arrogantprogrammer.domain.OrderRecord;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/rest-synchronous")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RESTResource {

    static final Logger LOGGER = LoggerFactory.getLogger(RESTResource.class);

    @RestClient
    RESTClientSynchronous restClientSynchronous;

    @GET
    public List<OrderRecord> allOrders() {
        List<OrderRecord> allOrders = restClientSynchronous.allOrders();
        LOGGER.debug("allOrders: {}", allOrders);
        return allOrders;
    }

}
