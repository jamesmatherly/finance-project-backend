package com.jamesmatherly.sample.project.service;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.jamesmatherly.sample.project.dto.YahooFinanceSummaryDto;
import com.jamesmatherly.sample.project.mapper.YahooMapper;
import com.jamesmatherly.sample.project.model.FinancialData;
import com.jamesmatherly.sample.project.model.FinnhubData;
import com.jamesmatherly.sample.project.model.YahooSummaryResponse;

import lombok.extern.java.Log;

@Service
@Log
public class StockService {
    
    @Autowired
    public YahooMapper yahooMapper;

    @Autowired
    RestTemplate template;

    @Value("${finnhub.token}")
    private String FINNHUB_TOKEN;

    public YahooFinanceSummaryDto getSummaryFromYahoo(String ticker) {
        YahooFinanceSummaryDto result =  new YahooFinanceSummaryDto();
        try {
            UriComponentsBuilder uBuilder = UriComponentsBuilder.fromUriString("https://query1.finance.yahoo.com")
                .path("/v11/finance/quoteSummary/")
                .path(ticker)
                .queryParam("modules", "financialData");
            ResponseEntity<YahooSummaryResponse> response = template.getForEntity(uBuilder.build().toUri(), YahooSummaryResponse.class);
            
            if (response.getBody() != null) {
                FinancialData data = response.getBody().getQuoteSummary().getResult().get(0).get("financialData");
                result = yahooMapper.finDataToDto(data);
            }
            template.close();
        } catch (NullPointerException | IOException e) {
            if (yahooMapper == null) {
                log.info("Mapper not injected properly");
            }
            log.info("Error when retrieving entity for ticker " + ticker);
        }
        return result;
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
}
