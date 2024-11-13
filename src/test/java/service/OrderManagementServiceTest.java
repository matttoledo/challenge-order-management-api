package service;

import client.OrderDynamoDBClientMock;
import client.ProductsApiClientMock;
import client.RedisClientMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouts.orders_manegement_api.client.RedisClient;
import com.mouts.orders_manegement_api.dto.OrderDTO;
import com.mouts.orders_manegement_api.service.OrderManagementService;
import com.mouts.orders_manegement_api.service.OrderService;
import com.mouts.orders_manegement_api.service.ProductService;
import mockpayload.OrderInputPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderManagementServiceTest {

    private ProductService productService;

    private OrderService orderService;

    private RedisClientMock redisClientMock;

    private OrderDynamoDBClientMock orderDynamoDBClientMock;

    private ObjectMapper objectMapper;

    private OrderManagementService orderManagementService;

    @BeforeEach
    public void initMocks() {
        this.productService = new ProductService(new ProductsApiClientMock());
        this.redisClientMock = new RedisClientMock();
        this.objectMapper = new ObjectMapper();
        this.orderDynamoDBClientMock = new OrderDynamoDBClientMock();
        this.orderService = new OrderService(orderDynamoDBClientMock, objectMapper);
        this.orderManagementService = new OrderManagementService(productService, orderService, redisClientMock, objectMapper);
    }


    @Test
    public void savinOrderTest() throws Exception {
        orderManagementService.createOrder(objectMapper.readValue(OrderInputPayload.PAYLOAD, OrderDTO.class));
        assertEquals(26.48, orderDynamoDBClientMock.getOrder("12").getPrice());

    }

    @Test
    public void redisIntegrationTest() throws Exception {
        orderManagementService.createOrder(objectMapper.readValue(OrderInputPayload.PAYLOAD, OrderDTO.class));
        orderManagementService.getOrderById("12");
        assertEquals(2, objectMapper.readValue(redisClientMock.get("order-12"), OrderDTO.class).getProducts().size());

    }


}
