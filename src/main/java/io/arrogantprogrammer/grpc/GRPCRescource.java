package io.arrogantprogrammer.grpc;

import io.arrogantprogrammer.domain.*;
import io.arrogantprogrammer.proto.*;
import io.quarkus.grpc.GrpcClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Path("/grpc")
public class GRPCRescource {

    static final Logger LOGGER = LoggerFactory.getLogger(GRPCRescource.class);

    @GrpcClient("grpc-orders-client")
    gRPCService grpcServiceClient;

    @GET
    public List<OrderRecord> allOrders() {
        List<OrderRecord> allOrders = grpcServiceClient.allOrders(Empty.newBuilder().build())
                .onItem()
                .transform(allOrderRecordsProto -> {
                    List<OrderRecord> orderRecords = new ArrayList<>();
                    allOrderRecordsProto.getOrderRecordsList().forEach(orderRecordProto -> {
                        OrderRecord orderRecord = new OrderRecord(Long.valueOf(orderRecordProto.getId()), orderRecordProto.getName(), MenuItem.values()[orderRecordProto.getMenuItem().getNumber()], OrderStatus.values()[orderRecordProto.getOrderStatus().getNumber()], PaymentStatus.values()[orderRecordProto.getPaymentStatus().getNumber()]);
                        orderRecords.add(orderRecord);
                    });
                    return orderRecords;
                }).await().indefinitely();
        LOGGER.debug("allOrders: {}", allOrders);
        return allOrders;
    }

    @POST
    public OrderRecord placeOrder(PlaceOrderCommand placeOrderCommand) {
        PlaceOrderProto placeOrderProto = PlaceOrderProto.newBuilder()
                .setMenuItem(MenuItemProto.forNumber(placeOrderCommand.menuItem().ordinal()))
                .setOrderName(placeOrderCommand.name())
                .build();
        OrderRecord orderRecord = grpcServiceClient.placeOrder(placeOrderProto)
                .onItem()
                .transform(orderRecordProto -> {
                    return new OrderRecord(Long.valueOf(orderRecordProto.getId()), orderRecordProto.getName(), MenuItem.values()[orderRecordProto.getMenuItem().getNumber()], OrderStatus.values()[orderRecordProto.getOrderStatus().getNumber()], PaymentStatus.values()[orderRecordProto.getPaymentStatus().getNumber()]);
                }).await().indefinitely();
        LOGGER.debug("orderRecord: {}", orderRecord);
        return orderRecord;
    }

    @GET
    @Path("/inprogress")
    public List<OrderRecord> inProgressOrders() {
        return new ArrayList<>();//restClientSynchronous.allOrders().stream().filter(orderRecord -> orderRecord.orderStatus().equals(OrderStatus.PENDING)).toList();
    }
}
