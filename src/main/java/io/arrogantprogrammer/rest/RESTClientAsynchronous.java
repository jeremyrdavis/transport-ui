package io.arrogantprogrammer.rest;

import io.arrogantprogrammer.domain.OrderRecord;
import io.arrogantprogrammer.domain.PlaceOrderCommand;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "asynchronous")
public interface RESTClientAsynchronous {

    @GET
    public Uni<List<OrderRecord>> allOrders();

    @POST
    public Uni<OrderRecord> placeOrderMutiny(PlaceOrderCommand placeOrderCommand);

    @POST
    public void placeOrder(PlaceOrderCommand placeOrderCommand);
}
