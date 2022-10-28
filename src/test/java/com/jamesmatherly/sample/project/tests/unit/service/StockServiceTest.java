package com.jamesmatherly.sample.project.tests.unit.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;

import com.jamesmatherly.sample.project.dto.YahooFinanceSummaryDto;
import com.jamesmatherly.sample.project.model.YahooSummaryField;
import com.jamesmatherly.sample.project.model.YahooSummaryResponse;
import com.jamesmatherly.sample.project.service.StockService;
import com.jamesmatherly.sample.project.tests.unit.BaseUnitTest;

@SpringBootTest
public class StockServiceTest extends BaseUnitTest {

    @InjectMocks
    StockService service;

    @Test
    public void getSummaryFromYahooSuccess() throws Exception {
        ClassPathResource responseFile = new ClassPathResource("YahooFinanceResponseJson/v11/finance/quoteSummary/TickerGmeResponse.json");
        String responseString = StreamUtils.copyToString(responseFile.getInputStream(), Charset.defaultCharset());
        YahooSummaryResponse responseObject = mapper.readValue(responseString, YahooSummaryResponse.class);
        when(template.getForEntity(any(), any())).thenReturn(new ResponseEntity<>(responseObject, HttpStatus.OK));

        YahooSummaryField expectField = new YahooSummaryField();
        expectField.setFmt("220.00");
        expectField.setRaw("220.0");
        
        YahooFinanceSummaryDto result = service.getSummaryFromYahoo("aapl");
        assertEquals(expectField.getFmt(), result.getTargetHighPrice().getFmt());
        assertEquals(expectField.getRaw(), result.getTargetHighPrice().getRaw());
    }
}
