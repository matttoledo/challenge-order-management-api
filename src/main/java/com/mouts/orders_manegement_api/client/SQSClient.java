package com.mouts.orders_manegement_api.client;

public interface SQSClient {
    void sendMessage(String queue, String payload);
}
