package com.jamesmatherly.sample.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jamesmatherly.sample.project.dto.YahooFinanceSummaryDto;
import com.jamesmatherly.sample.project.model.FinnhubData;
import com.jamesmatherly.sample.project.service.StockService;

@RestController
@CrossOrigin(maxAge = 3600)
public class StockController {

    @Autowired
    StockService service;
    
    @GetMapping("/controller")
    public YahooFinanceSummaryDto getYahooSummary(@RequestParam String ticker) {
        return service.getSummaryFromYahoo(ticker);
    }
    
    @GetMapping("/finnhubQuote")
    public FinnhubData getFinnhubSummary(@RequestParam String ticker) {
        return service.getSummaryFromFinnhub(ticker);
    }

    @GetMapping("/yahooRecommendation")
    public String getYahooRec(@RequestParam String ticker) {
        return service.getSummaryFromYahoo(ticker).getRecommendationKey();
    }

    @GetMapping("/priceCheck")
    public String getCurrentPrice(@RequestParam String ticker) {
        return service.getSummaryFromYahoo(ticker).getCurrentPrice().getRaw();
    }

}
