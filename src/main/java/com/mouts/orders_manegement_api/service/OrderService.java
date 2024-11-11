package com.mouts.orders_manegement_api.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouts.orders_manegement_api.client.OrderDynamoDBClient;
import com.mouts.orders_manegement_api.client.RedisClient;
import com.mouts.orders_manegement_api.dto.OrderDTO;
import com.mouts.orders_manegement_api.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.mouts.orders_manegement_api.constants.Constants.ORDER_CACHE_PREFIX;

@Component
@AllArgsConstructor
public class OrderService {

    private OrderDynamoDBClient orderDynamoDBClient;

    private RedisClient redisClient;

    private ObjectMapper objectMapper;

    public void createOrder(OrderDTO orderDTO){
        orderDynamoDBClient.saveOrder(dtoToEntity(orderDTO));
    }

    public Order recoverOrderById(String id) throws Exception {

        if(redisClient.get(ORDER_CACHE_PREFIX+id) == null){
            var order = orderDynamoDBClient.getOrder(id);
            if(order != null)
                redisClient.setex(ORDER_CACHE_PREFIX+id,5, objectMapper.writeValueAsString(order));
        }
        return orderDynamoDBClient.getOrder(id);
    }



    public Order dtoToEntity(OrderDTO orderDTO){
        var order = new Order();
        order.setId(orderDTO.getId());
        order.setProducts(orderDTO.getProducts());
        order.setPrice(orderDTO.getPrice());
        order.setStatus(orderDTO.getStatus());
        return order;

    }

    public OrderDTO entityToDTO(Order order){
        var orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setProducts(order.getProducts());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

}
