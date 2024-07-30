package com.jamesmatherly.sample.project.service;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesmatherly.sample.project.dto.AlphaVantageTimeSeriesDaily;
import com.jamesmatherly.sample.project.dto.FinnhubData;
import com.jamesmatherly.sample.project.dto.PolygonDailyOpenClose;
import com.jamesmatherly.sample.project.dynamo.Trade;
import com.jamesmatherly.sample.project.dynamo.TradeRepository;
import com.jamesmatherly.sample.project.mapper.StockDataMapper;
import com.jamesmatherly.sample.project.model.HistoricStockData;
import com.jamesmatherly.sample.project.model.StockData;

import lombok.extern.java.Log;

@Service
@Log
public class StockService {

    @Autowired
    RestTemplate template;

    @Autowired
    @Lazy
    TradeRepository tradeRepository;

    @Autowired
    StockDataMapper stockDataMapper;

    @Value("${finnhub.token}")
    private String FINNHUB_TOKEN;

    @Value("${polygon.token}")
    private String POLYGON_TOKEN;

    @Value("${alphavantage.token}")
    private String ALPHAVANTAGE_TOKEN;

    public StockData geStockData(String ticker) {
        try {
            StockData result = stockDataMapper.finDataToDto(getSummaryFromFinnhub(ticker));
            result.setTicker(ticker);
            return result;
        } catch (NullPointerException e) {
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
            return response.getBody();
        } catch (NullPointerException e) {
            log.info("Error when retrieving entity for ticker " + ticker);
            return null;
        }
    }

    public PolygonDailyOpenClose getPolygonDailyOpenClose(String ticker, LocalDate date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            UriComponentsBuilder uBuilder = UriComponentsBuilder.fromUriString("https://api.polygon.io/v1/")
                .path(String.format("open-close/%s/%s", ticker, date.format(df)));
            RequestEntity<Void> request = RequestEntity.get(uBuilder.build().toUri())
                .header("Authorization", "Bearer " + POLYGON_TOKEN)
                .build();
            try {
                ResponseEntity<PolygonDailyOpenClose> response = template.exchange(request, PolygonDailyOpenClose.class);
                return response.getBody();
            } catch (HttpClientErrorException.NotFound e) {
                log.warning(String.format("No data for %s on %s", ticker, date.format(df)));
            }
            
            return new PolygonDailyOpenClose();
        } catch (NullPointerException e) {
            log.info("Error when retrieving entity for ticker " + ticker);
            return null;
        }
    }

    public AlphaVantageTimeSeriesDaily getAlphaVantageTimeSeriesDaily(String ticker) {
        try {
            File jsonFile = new File(String.format("src/main/resources/AlphaVantage Time Series/%s.json", ticker));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonFile, AlphaVantageTimeSeriesDaily.class);
        } catch (IOException e) {
            UriComponentsBuilder uBuilder = UriComponentsBuilder.fromUriString("https://www.alphavantage.co/")
                .path("query")
                .queryParam("function", "TIME_SERIES_DAILY")
                .queryParam("symbol", ticker)
                .queryParam("apikey", ALPHAVANTAGE_TOKEN);
                RequestEntity<Void> request = RequestEntity.get(uBuilder.build().toUri()).build();
            ResponseEntity<AlphaVantageTimeSeriesDaily> response = template.exchange(request, AlphaVantageTimeSeriesDaily.class);
            return response.getBody();
        }
    }

    public String executeTrade(Trade trade) {
        StockData data = geStockData(trade.getTicker());
        trade.setExecutionTime(LocalDateTime.now().toString());
        trade.setValue(data.getStockPrice());
        trade.setId(UUID.randomUUID().toString());
        return tradeRepository.executeTrade(trade);
    }

    public Trade getTrade(String id, String executionTime) {
        return tradeRepository.getById(id, executionTime);
    }
    
    public HistoricStockData getHistoricStockData(String ticker, LocalDate startDate, LocalDate endDate) {
        try {
            HistoricStockData result = new HistoricStockData();
            ArrayList<StockData> history = new ArrayList<>();
            int intervals = 0;
            for (LocalDate d = startDate; d.isBefore(endDate); d = d.plusDays(1)) {
                intervals++;
                StockData stock = stockDataMapper.polygonDailyToStockData(getPolygonDailyOpenClose(ticker, d));
                stock.setTicker(ticker);
                history.add(stock);
            }
            result.setTicker(ticker);
            result.setEndDate(endDate);
            result.setStarDate(startDate);
            result.setIntervals(intervals);
            result.setHistory(history);
            return result;
        } catch (NullPointerException e) {
            log.info("Error when retrieving entity for ticker " + ticker);
            return null;
        }
    }
}
