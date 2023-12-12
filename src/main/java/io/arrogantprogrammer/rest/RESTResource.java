package io.arrogantprogrammer.rest;

import io.arrogantprogrammer.domain.OrderRecord;
import io.arrogantprogrammer.domain.OrderStatus;
import io.arrogantprogrammer.domain.PlaceOrderCommand;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/rest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RESTResource {

    static final Logger LOGGER = LoggerFactory.getLogger(RESTResource.class);

    @RestClient
    RESTClientSynchronous restClientSynchronous;

    @RestClient
    RESTClientAsynchronous restClientAsynchronous;

    @GET
    public List<OrderRecord> allOrders() {
        List<OrderRecord> allOrders = restClientSynchronous.allOrders();
        LOGGER.debug("allOrders: {}", allOrders);
        return allOrders;
    }

    @POST
    @Path("/synchronous")
    public OrderRecord placeOrder(PlaceOrderCommand placeOrderCommand) {
        OrderRecord orderRecord = restClientSynchronous.placeOrder(placeOrderCommand);
        LOGGER.debug("orderRecord: {}", orderRecord);
        return orderRecord;
    }

    @POST
    @Path("/asynchronous")
    public Response placeOrderAsync(PlaceOrderCommand placeOrderCommand) {
        restClientAsynchronous.placeOrder(placeOrderCommand);
        LOGGER.debug("order sent: {}", placeOrderCommand);
        return Response.accepted().build();
    }

    @GET
    @Path("/inprogress")
    public List<OrderRecord> inProgressOrders() {
        return restClientSynchronous.allOrders().stream().filter(orderRecord -> orderRecord.orderStatus().equals(OrderStatus.PENDING)).toList();
    }
}
