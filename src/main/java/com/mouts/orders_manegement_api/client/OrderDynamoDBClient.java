package com.mouts.orders_manegement_api.client;

import com.mouts.orders_manegement_api.entity.Order;

public interface OrderDynamoDBClient {
    void saveOrder(Order order);

    Order getOrder(String partitionKey);

}
