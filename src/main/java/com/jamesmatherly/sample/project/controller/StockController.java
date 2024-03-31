package com.jamesmatherly.sample.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jamesmatherly.sample.project.dto.FinnhubData;
import com.jamesmatherly.sample.project.dynamo.Trade;
import com.jamesmatherly.sample.project.service.StockService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@CrossOrigin(maxAge = 3600)
public class StockController {

    @Autowired
    StockService service;
    
    @GetMapping("/finnhubQuote")
    public FinnhubData getFinnhubSummary(@RequestParam String ticker) {
        return service.getSummaryFromFinnhub(ticker);
    }

    @GetMapping("/priceCheck")
    public float getCurrentPrice(@RequestParam String ticker) {
        return service.geStockData(ticker).getStockPrice();
    }

    @PostMapping("/trade")
    public String executeTrade(@RequestParam String ticker, @RequestParam double quantity) {
        return service.executeTrade(ticker, "BUY", "MARKET", quantity);
    }

    @GetMapping("/trade")
    public Trade getTrade(@RequestParam String id, @RequestParam String executionTime) {
        return service.getTrade(id, executionTime);
    }
    
    

}
