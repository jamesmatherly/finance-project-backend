package com.jamesmatherly.sample.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jamesmatherly.sample.project.dto.YahooFinanceSummaryDto;
import com.jamesmatherly.sample.project.service.StockService;

@RestController
public class StockController {

    @Autowired
    StockService service;
    
    @GetMapping("/controller")
    public YahooFinanceSummaryDto getYahooSummary(@RequestParam String ticker) {
        return service.getSummaryFromYahoo(ticker);
    }

}
