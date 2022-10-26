package com.jamesmatherly.sample.project.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.jamesmatherly.sample.project.dto.YahooFinanceSummaryDto;
import com.jamesmatherly.sample.project.model.YahooSummaryResponse;

@Service
public class StockService {
    public YahooFinanceSummaryDto getSummaryFromYahoo(String ticker) throws NullPointerException {
        RestTemplate template = new RestTemplate();
        UriComponentsBuilder uBuilder = UriComponentsBuilder.fromUriString("https://query1.finance.yahoo.com")
            .path("/v11/finance/quoteSummary/")
            .path(ticker)
            .queryParam("modules", "financialData");
        ResponseEntity<YahooSummaryResponse> response = template.getForEntity(uBuilder.build().toUri(), YahooSummaryResponse.class);
        YahooFinanceSummaryDto result =  new YahooFinanceSummaryDto();
        result.setTargetHighPrice(response.getBody().getQuoteSummary().getResult().get(0).get("financialData").getTargetHighPrice());
        return result;
    }
}
