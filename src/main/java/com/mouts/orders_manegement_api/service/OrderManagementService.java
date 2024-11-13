package com.mouts.orders_manegement_api.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouts.orders_manegement_api.client.RedisClient;
import com.mouts.orders_manegement_api.dto.OrderDTO;
import com.mouts.orders_manegement_api.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ForkJoinPool;

import static com.mouts.orders_manegement_api.constants.Constants.ORDER_CACHE_PREFIX;

@Component
@AllArgsConstructor
public class OrderManagementService {

    private ProductService productService;

    private OrderService orderService;

    private RedisClient redisClient;

    private ObjectMapper objectMapper;

    private void calculateProductsValue(OrderDTO orderDTO) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        var totalPrice = forkJoinPool.submit(() ->
                orderDTO.getProducts().parallelStream()
                        .peek(product -> {
                            var productPrice = productService.getPriceByProductId(product.getId());
                            product.setPrice(productPrice);
                        })
                        .mapToDouble(ProductDTO::getPrice)
                        .sum()
        ).join();
        orderDTO.setPrice(totalPrice);
    }


    public OrderDTO getOrderById(String id) throws Exception {
        var payload = redisClient.get(String.format(ORDER_CACHE_PREFIX, id));
        if(payload == null){
            redisClient.setex(String.format(ORDER_CACHE_PREFIX, id), 36000, objectMapper.writeValueAsString(orderService.entityToDTO(orderService.recoverOrderById(id))));
        }
        return objectMapper.readValue(redisClient.get(String.format(ORDER_CACHE_PREFIX, id)), OrderDTO.class);
    }

    public void createOrder(OrderDTO orderDTO) throws Exception {
//        if(orderDTO.getId() == null  || orderDTO.getProducts() == null || orderDTO.getId().isEmpty() || orderDTO.getProducts().isEmpty())
//            throw new EmptyParamException("id and products could not be null");

        try{
            calculateProductsValue(orderDTO);
            orderDTO.setStatus("DONE");
        }catch (Exception e){
            orderDTO.setStatus("FAIL");
        }
        orderService.createOrder(orderDTO);
    }
}
