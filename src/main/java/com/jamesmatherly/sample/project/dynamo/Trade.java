package com.jamesmatherly.sample.project.dynamo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Trade {
    @DynamoDbPartitionKey
    public String getId() {
        return this.id;
    }

    private String id;

    private String positionId;

    private String portfolioId;
    
    private String tradeType;
    
    private String executionType;
    
    @DynamoDbSortKey
    public String getExecutionTime() {
        return this.executionTime;
    }
    private String executionTime;
    
    private double quantity;
    
    private String name;
    
    private float value;
    
    private String ticker;
    
}
