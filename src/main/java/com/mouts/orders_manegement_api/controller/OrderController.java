package com.mouts.orders_manegement_api.controller;


import com.mouts.orders_manegement_api.dto.OrderDTO;
import com.mouts.orders_manegement_api.service.OrderManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private OrderManagementService orderManagementService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok().body(orderManagementService.getOrderById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws Exception {
        orderManagementService.createOrder(orderDTO);
        return ResponseEntity.ok().build();
    }
}
