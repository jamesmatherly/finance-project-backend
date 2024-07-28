package com.jamesmatherly.sample.project.dynamo;

import com.jamesmatherly.sample.project.model.TradeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
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

    @DynamoDbSecondaryPartitionKey(indexNames = "portfolio-index")
    public String getPortfolioId() {
        return this.portfolioId;
    }
    private String portfolioId;
    
    private TradeType tradeType;
    
    private String executionType;
    
    @DynamoDbSortKey
    public String getExecutionTime() {
        return this.executionTime;
    }
    private String executionTime;
    
    private float quantity;
    
    private String name;
    
    private float value;
    
    private String ticker;
    
}
