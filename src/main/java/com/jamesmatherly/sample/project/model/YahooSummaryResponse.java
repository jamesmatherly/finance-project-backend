package com.jamesmatherly.sample.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YahooSummaryResponse {
    QuoteSummary quoteSummary;
}
