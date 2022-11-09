package com.jamesmatherly.sample.project.tests.unit;

import java.nio.charset.Charset;

import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseUnitTest {
    @Mock
    protected RestTemplate template;

    protected ObjectMapper mapper = new ObjectMapper();

    protected <T> ResponseEntity<Object> createEntity(String content, Class<T> valueType, HttpStatus status) throws Exception {
        ClassPathResource responseFile = new ClassPathResource("YahooFinanceResponseJson/v11/finance/quoteSummary/TickerGmeResponse.json");
        String responseString = StreamUtils.copyToString(responseFile.getInputStream(), Charset.defaultCharset());
        T responseObject = mapper.readValue(responseString, valueType);
        return new ResponseEntity<Object>(responseObject, status);
    }
    
}
