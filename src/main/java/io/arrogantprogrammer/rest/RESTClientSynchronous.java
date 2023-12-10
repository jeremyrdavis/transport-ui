package io.arrogantprogrammer.rest;

import io.arrogantprogrammer.domain.OrderRecord;
import io.arrogantprogrammer.domain.PlaceOrderCommand;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "synchronous")
public interface RESTClientSynchronous {

    @GET
    public List<OrderRecord> allOrders();

    @POST
    public OrderRecord placeOrder(PlaceOrderCommand placeOrderCommand);
}
