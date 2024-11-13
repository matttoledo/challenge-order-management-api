package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mouts.orders_manegement_api.client.ProductsApi;
import com.mouts.orders_manegement_api.dto.ProductDTO;
import mockpayload.ProductsApiPayload;
import org.apache.camel.Produce;

import java.util.HashMap;

public class ProductsApiClientMock implements ProductsApi {

    private ObjectMapper objectMapper;
    @Override
    public Double getProductPriceById(String id) {
        ProductDTO[] products = null;
        try {
            products = objectMapper.readValue(ProductsApiPayload.PAYLOAD, ProductDTO[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        var productsMap = new HashMap<String, ProductDTO>();

        for (var product : products){
            productsMap.put(product.getId(), product);
        }
        return productsMap.get(id).getPrice();
    }
}
