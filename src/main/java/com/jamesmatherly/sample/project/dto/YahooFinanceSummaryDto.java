package com.jamesmatherly.sample.project.dto;

import com.jamesmatherly.sample.project.model.YahooSummaryField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YahooFinanceSummaryDto {

    YahooSummaryField targetHighPrice;
}
