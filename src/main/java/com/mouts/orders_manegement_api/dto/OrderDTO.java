package com.mouts.orders_manegement_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {
    private String id;
    private List<ProductDTO> products;
    private Double price;
    private String status;
}
