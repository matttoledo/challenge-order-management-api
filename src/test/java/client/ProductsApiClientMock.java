package client;

import com.mouts.orders_manegement_api.client.ProductsApi;
import org.apache.camel.Produce;

public class ProductsApiClientMock implements ProductsApi {
    @Override
    public Double getProductById(String id) {
        return 0.0;
    }
}
