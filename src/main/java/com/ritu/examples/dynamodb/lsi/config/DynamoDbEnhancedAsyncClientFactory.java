package com.ritu.examples.dynamodb.lsi.config;


import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import javax.inject.Singleton;
import java.net.URI;

@Factory
public class DynamoDbEnhancedAsyncClientFactory {
    @Singleton
    @Replaces(DynamoDbEnhancedAsyncClient.class)
    public DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient() {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(DynamoDbAsyncClient.builder()
                        .region(Region.US_WEST_2).endpointOverride(URI.create("http://localhost:8000")).endpointDiscoveryEnabled(false)
                        .build())
                .build();
    }

}