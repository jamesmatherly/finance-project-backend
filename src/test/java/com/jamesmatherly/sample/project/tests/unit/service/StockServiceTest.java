package com.jamesmatherly.sample.project.tests.unit.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jamesmatherly.sample.project.dto.YahooFinanceSummaryDto;
import com.jamesmatherly.sample.project.mapper.YahooMapper;
import com.jamesmatherly.sample.project.mapper.YahooMapperImpl;
import com.jamesmatherly.sample.project.model.YahooSummaryField;
import com.jamesmatherly.sample.project.model.YahooSummaryResponse;
import com.jamesmatherly.sample.project.service.StockService;
import com.jamesmatherly.sample.project.tests.unit.BaseUnitTest;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StockServiceTest extends BaseUnitTest {

    @Spy
    public YahooMapper yahooMapper = new YahooMapperImpl();

    @InjectMocks
    StockService service;

    @Test
    public void getSummaryFromYahooSuccess() throws Exception {
        when(baseTemplate.getForEntity(any(), any()))
        .thenReturn(createEntity("YahooFinanceResponseJson/v11/finance/quoteSummary/TickerGmeResponse.json",
            YahooSummaryResponse.class,
            HttpStatus.OK));

        YahooSummaryField expectField = new YahooSummaryField();
        expectField.setFmt("220.00");
        expectField.setRaw("220.0");
        
        YahooFinanceSummaryDto result = service.getSummaryFromYahoo("aapl");
        assertEquals(expectField.getFmt(), result.getTargetHighPrice().getFmt());
        assertEquals(expectField.getRaw(), result.getTargetHighPrice().getRaw());
    }
}
