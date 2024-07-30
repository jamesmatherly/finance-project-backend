package com.jamesmatherly.sample.project.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PolygonDailyOpenClose {
    float afterHours;
    float close;
    Date from;
    float high;
    float low;
    float open;
    float preMarket;
    String status;
    String symbol;
    int volume;
}
