package com.jamesmatherly.sample.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialData {
    String maxAge;
    String recommendationKey;
    String financialCurrency;
    YahooSummaryField currentPrice;
    YahooSummaryField targetHighPrice;
    YahooSummaryField targetLowPrice;
    YahooSummaryField targetMeanPrice;
    YahooSummaryField targetMedianPrice;
    YahooSummaryField recommendationMean;
    YahooSummaryField numberOfAnalystOpinions;
    YahooSummaryField totalCash;
    YahooSummaryField totalCashPerShare;
    YahooSummaryField ebitda;
    YahooSummaryField totalDebt;
    YahooSummaryField quickRatio;
    YahooSummaryField currentRatio;
    YahooSummaryField totalRevenue;
    YahooSummaryField debtToEquity;
    YahooSummaryField revenuePerShare;
    YahooSummaryField returnOnAssets;
    YahooSummaryField returnOnEquity;
    YahooSummaryField grossProfits;
    YahooSummaryField freeCashflow;
    YahooSummaryField operatingCashflow;
    YahooSummaryField earningsGrowth;
    YahooSummaryField revenueGrowth;
    YahooSummaryField grossMargins;
    YahooSummaryField ebitdaMargins;
    YahooSummaryField operatingMargins;
    YahooSummaryField profitMargins;
}