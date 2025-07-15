package com.sewon.dsl.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ElectronicAssetQueryResponseDto {

    // Additional fields for electronic assets
    private String cpu;
    private Integer ram;
    private BigDecimal storage;
    private String gpu;


}

