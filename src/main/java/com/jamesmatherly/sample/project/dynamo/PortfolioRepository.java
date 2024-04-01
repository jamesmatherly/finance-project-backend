package com.jamesmatherly.sample.project.dynamo;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

@Repository
@AllArgsConstructor
public class PortfolioRepository {

    private final DynamoDbTable<Portfolio> portfolioTable;

    @Autowired
    public PortfolioRepository(DynamoDbEnhancedClient dynamo) {
        this.portfolioTable = dynamo.table("portfolios", TableSchema.fromBean(Portfolio.class));
    }

    public Portfolio updatePortfolio(Portfolio portfolio) {
        portfolioTable.putItem(portfolio);
        return portfolio;
    }

    public Portfolio getById(String id) {
        Iterator<Portfolio> i = portfolioTable.query(QueryConditional.keyEqualTo(Key.builder().partitionValue(id).build())).items().iterator();
        return i.hasNext() ? i.next() : null;
    }

    public PageIterable<Portfolio> getByUsername(String username) {
        return portfolioTable.query(QueryConditional.keyEqualTo(Key.builder().sortValue(username).build()));
    }
}
