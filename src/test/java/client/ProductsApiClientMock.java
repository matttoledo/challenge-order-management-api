package client;

import com.mouts.orders_manegement_api.client.ProductsApiClient;

import java.util.Map;

public class ProductsApiClientMock implements ProductsApiClient {

    @Override
    public Double getProductPriceById(String id) {
        var productMap = Map.of(
                "1", 10.99,
                "2", 15.49,
                "3", 8.75,
                "4", 20.00,
                "5", 5.50,
                "6", 12.30,
                "7", 18.99,
                "8", 25.00,
                "9", 9.99,
                "10", 30.00
        );
        return productMap.get(id);
    }
}
