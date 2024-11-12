package com.mouts.orders_manegement_api.service;


import com.mouts.orders_manegement_api.dto.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderManagementService {

    private ProductService productService;

    private OrderService orderService;

    private void calculateProductsValue(OrderDTO orderDTO){
        var result = 0.0;
        for (var product : orderDTO.getProducts()){
            result = productService.getPriceByProductId(product.getId()) + result;
        }
        orderDTO.setPrice(result);
    }


    public OrderDTO getOrderById(String id) throws Exception {
        var orderDTO = orderService.entityToDTO(orderService.recoverOrderById(id));
        calculateProductsValue(orderDTO);
        return orderDTO;
    }

    public void createOrder(OrderDTO orderDTO) throws Exception {
        orderService.createOrder(orderDTO);
    }
}
