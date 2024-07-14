package com.jamesmatherly.sample.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamesmatherly.sample.project.dynamo.Portfolio;
import com.jamesmatherly.sample.project.dynamo.PortfolioRepository;

import lombok.extern.java.Log;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

@Service
@Log
public class PortfolioService {
    @Autowired
    PortfolioRepository repository;

    public Portfolio getPortfolioById(String id) {
        return repository.getById(id);
    }

    public Portfolio updatePortfolio(Portfolio portfolio) {
        return repository.updatePortfolio(portfolio);
    }

    public SdkIterable<Page<Portfolio>> getPortfolioByUser(String username) {
        return repository.getByUsername(username);
    }
}
