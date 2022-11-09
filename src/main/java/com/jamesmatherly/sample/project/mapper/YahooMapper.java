package com.jamesmatherly.sample.project.mapper;

import org.mapstruct.Mapper;

import com.jamesmatherly.sample.project.dto.YahooFinanceSummaryDto;
import com.jamesmatherly.sample.project.model.FinancialData;

@Mapper(
    componentModel = "spring")
public interface YahooMapper {
    YahooFinanceSummaryDto finDataToDto (FinancialData data);
    
}
