package com.jamesmatherly.sample.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinnhubData {

    @JsonProperty("c")
    public void setcurrentPrice(String c) {
        currentPrice = c;
    }
    @JsonProperty("currentPrice")
    public String getcurrentPrice() {
        return currentPrice;
    }
    String currentPrice;
    
    @JsonProperty("d")
    public void setchange(String d) {
        change = d;
    }
    @JsonProperty("change")
    public String getchange() {
        return change;
    }
    String change;

    @JsonProperty("dp")
    public void setpercentChange(String dp) {
        percentChange = dp;
    }

    @JsonProperty("percentChange")
    public String getpercentChange() {
        return percentChange;
    }
    String percentChange;

    @JsonProperty("h")
    public void setdailyHigh(String h) {
        dailyHigh = h;
    }
    @JsonProperty("dailyHigh")
    public String getdailyHigh() {
        return dailyHigh;
    }
    String dailyHigh;

    @JsonProperty("l")
    public void setdailyLow(String l) {
        dailyLow = l;
    }
    @JsonProperty("dailyLow")
    public String getdailyLow() {
        return dailyLow;
    }
    String dailyLow;

    @JsonProperty("o")
    public void setdailyOpen(String o) {
        dailyOpen = o;
    }
    @JsonProperty("dailyOpen")
    public String getdailyOpen() {
        return dailyOpen;
    }
    String dailyOpen;

    @JsonProperty("pc")
    public void setprevClose(String pc) {
        prevClose = pc;
    }
    @JsonProperty("prevClose")
    public String getprevClose() {
        return prevClose;
    }
    String prevClose;

}