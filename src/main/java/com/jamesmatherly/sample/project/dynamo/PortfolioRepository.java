package com.jamesmatherly.sample.project.dynamo;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
public class PortfolioRepository {

    private final DynamoDbTable<Portfolio> portfolioTable;
    private final DynamoDbIndex<Portfolio> usernameIndex;

    @Autowired
    public PortfolioRepository(DynamoDbEnhancedClient dynamo) {
        this.portfolioTable = dynamo.table("portfolios", TableSchema.fromBean(Portfolio.class));
        usernameIndex = portfolioTable.index("username-index");
    }

    public Portfolio updatePortfolio(Portfolio portfolio) {
        portfolioTable.putItem(portfolio);
        return portfolio;
    }

    public Portfolio getById(String id) {
        Iterator<Portfolio> i = portfolioTable.query(QueryConditional.keyEqualTo(Key.builder().partitionValue(id).build())).items().iterator();
        return i.hasNext() ? i.next() : null;
    }

    public SdkIterable<Page<Portfolio>> getByUsername(String username) {
        SdkIterable<Page<Portfolio>> i = usernameIndex.query(QueryConditional.keyEqualTo(Key.builder().partitionValue(username).build()));
        return i;
    }
}
