package com.jamesmatherly.sample.project.dynamo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Portfolio {
    @DynamoDbPartitionKey
    public String getId() {
        return this.id;
    }
    String id;
    @DynamoDbSortKey
    public String getUsername() {
        return this.username;
    }
    String username;
    float availableFunds;
}
