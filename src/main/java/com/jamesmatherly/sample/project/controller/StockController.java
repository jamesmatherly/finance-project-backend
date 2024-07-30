package com.jamesmatherly.sample.project.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jamesmatherly.sample.project.dto.AlphaVantageTimeSeriesDaily;
import com.jamesmatherly.sample.project.dto.FinnhubData;
import com.jamesmatherly.sample.project.dynamo.Portfolio;
import com.jamesmatherly.sample.project.dynamo.Trade;
import com.jamesmatherly.sample.project.model.Position;
import com.jamesmatherly.sample.project.model.TradeType;
import com.jamesmatherly.sample.project.service.PortfolioService;
import com.jamesmatherly.sample.project.service.StockService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@CrossOrigin(maxAge = 3600)
public class StockController {

    @Autowired
    StockService stockService;

    @Autowired
    PortfolioService portfolioService;
    
    @GetMapping("/finnhubQuote")
    public FinnhubData getFinnhubSummary(@RequestParam String ticker) {
        return stockService.getSummaryFromFinnhub(ticker);
    }

    @GetMapping("/priceCheck")
    public float getCurrentPrice(@RequestParam String ticker) {
        return stockService.geStockData(ticker).getStockPrice();
    }

    @PostMapping("/trade")
    public String executeTrade(@ModelAttribute Trade trade, HttpServletResponse response) {
        Portfolio portfolio = portfolioService.getPortfolioById(trade.getPortfolioId());
        if (portfolio == null) {
            response.setStatus(400);
            return "Invalid portfolio";
        }
        HashMap<String, Position> positions = portfolioService.getPositions(portfolio);
        if (trade.getTradeType().equals(TradeType.SELL)
            && positions.get(trade.getTicker()).getQuantity() < trade.getQuantity()) {
                response.setStatus(400);
            return "Not enough shares";
        }

        return stockService.executeTrade(trade);
    }

    @GetMapping("/trade")
    public Trade getTrade(@RequestParam String id, @RequestParam String executionTime) {
        return stockService.getTrade(id, executionTime);
    }
    
    @GetMapping("/history")
    public AlphaVantageTimeSeriesDaily getMethodName(@RequestParam String ticker) {
        return stockService.getAlphaVantageTimeSeriesDaily(ticker);
    }
    
    

}
