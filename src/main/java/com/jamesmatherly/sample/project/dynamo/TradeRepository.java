package com.jamesmatherly.sample.project.dynamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import com.jamesmatherly.sample.project.model.TradeType;
import com.jamesmatherly.sample.project.service.PortfolioService;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

@Repository
@AllArgsConstructor
public class TradeRepository {

    private final DynamoDbTable<Trade> tradeTable;

    private final DynamoDbIndex<Trade> portfolioIndex;

    private PortfolioService portfolioService;

    @Autowired
    public TradeRepository(DynamoDbEnhancedClient dynamo, @Lazy PortfolioService portfolioService) {
        this.tradeTable = dynamo.table("trades", TableSchema.fromBean(Trade.class));
        this.portfolioService = portfolioService;
        this.portfolioIndex = tradeTable.index("portfolio-index");
    }

    public String executeTrade(Trade trade) {
        tradeTable.putItem(trade);
        Portfolio portfolio = portfolioService.getPortfolioById(trade.getPortfolioId());
        float availableFunds = portfolio.getAvailableFunds();
        if (trade.getTradeType().equals(TradeType.BUY)) {
            availableFunds -= trade.getValue() * trade.getQuantity();
        } else if (trade.getTradeType().equals(TradeType.SELL)) {
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

    public SdkIterable<Page<Trade>> getByPortfolioId(String portfolioId) {
        SdkIterable<Page<Trade>> i = portfolioIndex.query(QueryConditional.keyEqualTo(Key.builder().partitionValue(portfolioId).build()));
        return i;
    }
    
}
