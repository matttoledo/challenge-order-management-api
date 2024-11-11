package com.mouts.orders_manegement_api;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrdersApiApplication {

	@Value("${region}")
	private String region;

	public static final Integer MAX_CONNECTIONS = 500;

	public static void main(String[] args) {
		SpringApplication.run(OrdersApiApplication.class, args);
	}

	@Bean
	public ObjectMapper createObjectMapper() {
		var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Bean
	public AmazonDynamoDB createDynamoClient() {
		return AmazonDynamoDBClientBuilder.standard()
				.withRegion(region)
				.withCredentials(new DefaultAWSCredentialsProviderChain())
				.withClientConfiguration(new ClientConfiguration().withMaxConnections(MAX_CONNECTIONS)).build();
	}

	@Bean
	public DynamoDBMapperConfig createDynamoDBMapperConfig() {
		return DynamoDBMapperConfig.builder()
				.withConversionSchema(DynamoDBMapperConfig.DEFAULT.getConversionSchema()).build();
	}

	@Bean
	public DynamoDBMapper createDynamoDBMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
		return new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);
	}


}
