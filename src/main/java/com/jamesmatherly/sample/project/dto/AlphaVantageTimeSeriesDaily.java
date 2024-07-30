package com.jamesmatherly.sample.project.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AlphaVantageTimeSeriesDaily {

    @JsonProperty("Meta Data")
    private Map<String, String> metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, Map<String, Float>> data;
    
}
