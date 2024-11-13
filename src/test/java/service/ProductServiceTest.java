package service;

import client.ProductsApiClientMock;
import com.mouts.orders_manegement_api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceTest {

    private ProductsApiClientMock productsApiClientMock;

    private ProductService productService;
    @BeforeEach
    public void initMocks() {
        this.productsApiClientMock = new ProductsApiClientMock();
        this.productService = new ProductService(productsApiClientMock);
    }


    @Test
    public void receivingProductPricesTest() throws Exception {
        productService.getPriceByProductId("1");
        assertEquals(10.99, productsApiClientMock.getProductPriceById("1"));
        assertEquals(15.49, productsApiClientMock.getProductPriceById("2"));
        assertEquals(8.75, productsApiClientMock.getProductPriceById("3"));

    }
}
