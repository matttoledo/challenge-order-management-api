package client;

import com.mouts.orders_manegement_api.client.OrderDynamoDBClient;
import com.mouts.orders_manegement_api.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDynamoDBClientMock implements OrderDynamoDBClient {

    private final List<Order> recordsMock = new ArrayList<>();
    @Override
    public void saveOrder(Order order) {
        recordsMock.add(order);
    }

    @Override
    public Order getOrder(String partitionKey) {
        var orderMap = new HashMap<>();
        for (var record: recordsMock){
            orderMap.put(record.getId(), record);
        }
        return (Order) orderMap.getOrDefault(partitionKey, new Order());
    }

    public List<Order> getRecordsMock() {
        return recordsMock;
    }
}
