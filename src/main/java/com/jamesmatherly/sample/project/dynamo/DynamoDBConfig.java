package com.jamesmatherly.sample.project.dynamo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

    @Value("${aws.dynamodb.endpoint}")
    private String dynamodbEndpoint;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.dynamodb.accessKey}")
    private String dynamodbAccessKey;

    @Value("${aws.dynamodb.secretKey}")
    private String dynamodbSecretKey;

    @Value("${aws.dynamodb.sessionToken}")
    private String dynamodbSessionToken;

	@Bean
	DynamoDbClient amazonDynamoDBClient() {
		return getDynamoDbClient();
	}
	
	@Bean
	DynamoDbEnhancedClient amazonDynamoDBEnhancedClient() {
		return DynamoDbEnhancedClient.builder().dynamoDbClient(getDynamoDbClient()).build();
	}

    private DynamoDbClient getDynamoDbClient() {
		ClientOverrideConfiguration.Builder overrideConfig =
		ClientOverrideConfiguration.builder();

		return DynamoDbClient.builder()
		.overrideConfiguration(overrideConfig.build())
		.endpointOverride(URI.create("http://localhost:8000"))
		.region(Region.US_EAST_1)
		.build();
	}
}