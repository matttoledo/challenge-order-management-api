package com.mouts.orders_manegement_api.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "orders")
public class Order {
    @DynamoDBHashKey
    private String id;
    private String products;
    private Double price;
    private String status;
}
