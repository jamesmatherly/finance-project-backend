package com.jamesmatherly.sample.project.tests.unit;

import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseUnitTest {
    @Mock
    protected RestTemplate template;

    protected ObjectMapper mapper = new ObjectMapper();
    
}
