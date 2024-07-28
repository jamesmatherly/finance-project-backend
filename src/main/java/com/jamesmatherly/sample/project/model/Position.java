package com.jamesmatherly.sample.project.model;

import lombok.Data;

@Data
public class Position {
    private String ticker;
    private float costBasis;
    private String currentValue;
    private float quantity;
}
