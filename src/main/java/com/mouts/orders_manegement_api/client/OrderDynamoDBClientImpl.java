package com.mouts.orders_manegement_api.client;

import com.amazonaws.services.dynamodbv2.datamodeling.IDynamoDBMapper;
import com.mouts.orders_manegement_api.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderDynamoDBClientImpl implements OrderDynamoDBClient {

    private IDynamoDBMapper iDynamoDBMapper;

    @Override
    public void saveOrder(Order order) {
        iDynamoDBMapper.save(order);
    }

    @Override
    public Order getOrder(String partitionKey) {
        return iDynamoDBMapper.load(Order.class, partitionKey);
    }
}
