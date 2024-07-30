package com.jamesmatherly.sample.project.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class HistoricStockData {
    private String ticker;
    private int intervals;
    LocalDate starDate;
    LocalDate endDate;
    private List<StockData> history;
}
