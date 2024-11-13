package service;

import client.OrderDynamoDBClientMock;
import client.ProductsApiClientMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouts.orders_manegement_api.dto.OrderDTO;
import com.mouts.orders_manegement_api.service.OrderService;
import com.mouts.orders_manegement_api.service.ProductService;
import mockpayload.OrderInputPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTest {

    private OrderDynamoDBClientMock orderDynamoDBClientMock;

    private OrderService orderService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void initMocks() {
        this.objectMapper = new ObjectMapper();
        this.orderDynamoDBClientMock = new OrderDynamoDBClientMock();
        this.orderService = new OrderService(orderDynamoDBClientMock, objectMapper);
    }


    @Test
    public void createOrderTest() throws Exception {
        orderService.createOrder(objectMapper.readValue(OrderInputPayload.PAYLOAD, OrderDTO.class));
        assertEquals("12", orderDynamoDBClientMock.getOrder("12").getId());

    }
}
