package com.mouts.orders_manegement_api.service;

import com.mouts.orders_manegement_api.client.ProductsApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductService {

    private ProductsApi productsApi;


    public Double getPriceByProductId(String id){
        return productsApi.getProductById(id);
    }


}
