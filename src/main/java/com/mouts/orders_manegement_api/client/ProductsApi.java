package com.mouts.orders_manegement_api.client;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProductsApi {

    Double getProductPriceById(String id) throws JsonProcessingException;
}
