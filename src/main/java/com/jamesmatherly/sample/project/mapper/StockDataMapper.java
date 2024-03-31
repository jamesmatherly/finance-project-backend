package com.jamesmatherly.sample.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jamesmatherly.sample.project.dto.FinnhubData;
import com.jamesmatherly.sample.project.model.StockData;

@Mapper(
    componentModel = "spring")
public interface StockDataMapper {
    
    @Mapping(source = "currentPrice", target = "stockPrice")
    StockData finDataToDto (FinnhubData data);
}
