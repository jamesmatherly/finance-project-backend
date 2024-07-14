package com.jamesmatherly.sample.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.jamesmatherly.sample.project.dynamo.Portfolio;
import com.jamesmatherly.sample.project.service.PortfolioService;
import com.jamesmatherly.sample.project.service.UserService;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(maxAge = 3600)
public class PortfolioController {
    @Autowired
    PortfolioService portfolioService;

    @Autowired
    UserService userService;

    @GetMapping("/portfolio/id")
    public Portfolio getPortfolioById(@RequestParam String portfolioId){
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
        if (portfolio == null) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        }
        return portfolio;
    }

    @GetMapping("/portfolio/username")
    public List<Portfolio> getPortfolioByUser(@RequestParam String username) {
        ArrayList<Portfolio> portfolioList = new ArrayList();
        SdkIterable<Page<Portfolio>> portfolioIterator = portfolioService.getPortfolioByUser(username);
        portfolioIterator.forEach(page -> page.items().forEach(i -> portfolioList.add(i)));
        return portfolioList;
    }

    @PostMapping("/portfolio")
    public Portfolio createUpdatePortfolio(@RequestBody Portfolio entity) {
        portfolioService.updatePortfolio(entity);
        
        return entity;
    }
    
}
