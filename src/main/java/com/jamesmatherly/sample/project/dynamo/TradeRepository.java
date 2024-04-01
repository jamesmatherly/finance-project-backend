package com.jamesmatherly.sample.project.dynamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import com.jamesmatherly.sample.project.service.PortfolioService;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
@AllArgsConstructor
public class TradeRepository {

    private final DynamoDbTable<Trade> tradeTable;

    private PortfolioService portfolioService;

    @Autowired
    public TradeRepository(DynamoDbEnhancedClient dynamo, PortfolioService portfolioService) {
        this.tradeTable = dynamo.table("trades", TableSchema.fromBean(Trade.class));
        this.portfolioService = portfolioService;
    }

    public String executeTrade(Trade trade) {
        tradeTable.putItem(trade);
        Portfolio portfolio = portfolioService.getPortfolioById(trade.getPortfolioId());
        float availableFunds = portfolio.getAvailableFunds();
        if (trade.getTradeType().equals("BUY")) {
            availableFunds -= trade.getValue() * trade.getQuantity();
        } else if (trade.getTradeType().equals("SELL")) {
            availableFunds += trade.getValue() * trade.getQuantity();
        } else {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        }
        portfolio.setAvailableFunds(availableFunds);
        portfolioService.updatePortfolio(portfolio);
        return trade.getId();
    }

    public Trade getById(String id, String executionTime) {
        Trade trade = new Trade();
        trade.setId(id);
        trade.setExecutionTime(executionTime);
        return tradeTable.getItem(trade);
    }
    
}
