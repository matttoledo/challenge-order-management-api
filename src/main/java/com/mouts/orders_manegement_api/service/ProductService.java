package com.mouts.orders_manegement_api.service;

import com.mouts.orders_manegement_api.client.ProductsApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductService {

    private ProductsApiClient productsApiClient;


    public Double getPriceByProductId(String id) {
        return productsApiClient.getProductPriceById(id);
    }


}
