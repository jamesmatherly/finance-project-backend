package com.jamesmatherly.sample.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jamesmatherly.sample.project.dto.FinnhubData;
import com.jamesmatherly.sample.project.dto.PolygonDailyOpenClose;
import com.jamesmatherly.sample.project.model.StockData;

@Mapper(
    componentModel = "spring")
public interface StockDataMapper {
    
    @Mapping(target = "stockPrice", source = "currentPrice")
    StockData finDataToDto (FinnhubData data);

    @Mapping(target = "stockPrice", source = "close")
    @Mapping(target = "percentChange", expression = "java(Float.toString(100 * (data.getClose()-data.getOpen()) / data.getOpen()))")
    StockData polygonDailyToStockData(PolygonDailyOpenClose data);
}
