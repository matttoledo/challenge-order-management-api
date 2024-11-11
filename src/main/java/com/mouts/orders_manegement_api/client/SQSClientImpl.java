package com.mouts.orders_manegement_api.client;

import com.amazonaws.services.sqs.AmazonSQS;

public class SQSClientImpl implements SQSClient {

    private AmazonSQS amazonSQS;

    @Override
    public void sendMessage(String queue, String payload) {
        amazonSQS.sendMessage(queue, payload);
    }
}
