package com.mouts.orders_manegement_api;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class OrdersApiApplication {

	@Value("${region}")
	private String region;

	@Value("${writeRedisUrl}")
	private String writeRedisUrl;

	@Value("${readRedisUrl}")
	private String readRedisUrl;

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
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://dynamodb.railway.internal", region))
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("test", "test")))
				.withClientConfiguration(new ClientConfiguration().withMaxConnections(10))  // Configuração do cliente
				.build();
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

	@Bean
	@Primary
	@Qualifier("products-write")
	public RedisCommands<String, String> createWriteRedisClientProducts() {;
		RedisURI redisUri = RedisURI.create(writeRedisUrl);
		var client = RedisClient.create(redisUri);
		var connection = client.connect();
		return connection.sync();
	}

	@Bean
	@Qualifier("products-read")
	public RedisCommands<String, String> createReadRedisClientProducts() {
		RedisURI redisUri = RedisURI.create(readRedisUrl);
		var client = RedisClient.create(redisUri);
		var connection = client.connect();
		return connection.sync();
	}


}
