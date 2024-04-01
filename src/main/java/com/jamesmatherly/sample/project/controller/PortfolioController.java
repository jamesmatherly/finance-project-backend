package com.jamesmatherly.sample.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.jamesmatherly.sample.project.dynamo.Portfolio;
import com.jamesmatherly.sample.project.service.PortfolioService;

@RestController
@CrossOrigin(maxAge = 3600)
public class PortfolioController {
    @Autowired
    PortfolioService portfolioService;

    @GetMapping("/portfolio")
    public Portfolio getFinnhubSummary(@RequestParam String portfolioId){
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
        if (portfolio == null) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        }
        return portfolio;
    }
}
