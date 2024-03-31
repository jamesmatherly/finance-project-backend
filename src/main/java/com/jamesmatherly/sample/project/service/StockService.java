package com.jamesmatherly.sample.project.service;


import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.jamesmatherly.sample.project.dto.FinnhubData;
import com.jamesmatherly.sample.project.dynamo.Trade;
import com.jamesmatherly.sample.project.dynamo.TradeRepository;
import com.jamesmatherly.sample.project.mapper.StockDataMapper;
import com.jamesmatherly.sample.project.model.StockData;

import lombok.extern.java.Log;

@Service
@Log
public class StockService {

    @Autowired
    RestTemplate template;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    public StockDataMapper stockDataMapper;

    @Value("${finnhub.token}")
    private String FINNHUB_TOKEN;

    public StockData geStockData(String ticker) {
        try {
            UriComponentsBuilder uBuilder = UriComponentsBuilder.fromUriString("https://finnhub.io/api/v1/")
                .path("quote")
                .queryParam("symbol", ticker);
            RequestEntity<Void> request = RequestEntity.get(uBuilder.build().toUri())
                .header("X-Finnhub-Token", FINNHUB_TOKEN)
                .build();
            ResponseEntity<FinnhubData> response = template.exchange(request, FinnhubData.class);
            template.close();
            StockData result = stockDataMapper.finDataToDto(response.getBody());
            result.setTicker(ticker);
            return result;
        } catch (NullPointerException | IOException e) {
            log.info("Error when retrieving entity for ticker " + ticker);
            return null;
        }
    }

    public FinnhubData getSummaryFromFinnhub(String ticker) {
        try {
            UriComponentsBuilder uBuilder = UriComponentsBuilder.fromUriString("https://finnhub.io/api/v1/")
                .path("quote")
                .queryParam("symbol", ticker);
            RequestEntity<Void> request = RequestEntity.get(uBuilder.build().toUri())
                .header("X-Finnhub-Token", FINNHUB_TOKEN)
                .build();
            ResponseEntity<FinnhubData> response = template.exchange(request, FinnhubData.class);
            template.close();
            return response.getBody();
        } catch (NullPointerException | IOException e) {
            log.info("Error when retrieving entity for ticker " + ticker);
            return null;
        }
    }

    public String executeTrade(String ticker, String tradeType, String executionType, double quantity) {
        StockData data = geStockData(ticker);
        Trade trade = new Trade();
        trade.setPositionId("1");
        trade.setPortfolioId("1");
        trade.setTradeType(tradeType);
        trade.setExecutionType(executionType);
        trade.setExecutionTime(LocalDateTime.now().toString());
        trade.setQuantity(quantity);
        trade.setName(ticker);
        trade.setValue(data.getStockPrice());
        trade.setTicker(ticker);
        return tradeRepository.executeTrade(trade);
    }

    public Trade getTrade(String id, String executionTime) {
        return tradeRepository.getTradeById(id, executionTime);
    }
}
