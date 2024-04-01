package com.jamesmatherly.sample.project.dynamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
@AllArgsConstructor
public class TradeRepository {

    private final DynamoDbTable<Trade> tradeTable;

    @Autowired
    public TradeRepository(DynamoDbEnhancedClient dynamo) {
        this.tradeTable = dynamo.table("trades", TableSchema.fromBean(Trade.class));
    }

    public String executeTrade(Trade trade) {
        tradeTable.putItem(trade);
        return trade.getId();
    }

    public Trade getById(String id, String executionTime) {
        Trade trade = new Trade();
        trade.setId(id);
        trade.setExecutionTime(executionTime);
        return tradeTable.getItem(trade);
    }
    
}
