package com.mouts.orders_manegement_api.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouts.orders_manegement_api.client.OrderDynamoDBClient;
import com.mouts.orders_manegement_api.dto.OrderDTO;
import com.mouts.orders_manegement_api.dto.ProductDTO;
import com.mouts.orders_manegement_api.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class OrderService {

    private OrderDynamoDBClient orderDynamoDBClient;

    private ObjectMapper objectMapper;

    public void createOrder(OrderDTO orderDTO) throws Exception {
        orderDynamoDBClient.saveOrder(dtoToEntity(orderDTO));
    }

    public Order recoverOrderById(String id) {
        return orderDynamoDBClient.getOrder(id);
    }



    public Order dtoToEntity(OrderDTO orderDTO) throws Exception {
        var order = new Order();
        order.setId(orderDTO.getId());
        order.setProducts(objectMapper.writeValueAsString(orderDTO.getProducts()));
        order.setPrice(orderDTO.getPrice());
        order.setStatus(orderDTO.getStatus());
        return order;

    }

    public OrderDTO entityToDTO(Order order) throws Exception {
        var orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setProducts(Arrays.asList(objectMapper.readValue(order.getProducts(), ProductDTO[].class)));
        orderDTO.setPrice(order.getPrice());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

}
