package com.sewon.barcode.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class BarcodeTest {


    @DisplayName("바코드 값이 원하는대로 생성되는지 테스트")
    @Test
    void create_barcode_value() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String datePrefix = formatter.format(LocalDate.now());
        String assetDelimiterValue = "A" + 1L;
        String assetTypeDelimiterValue = "T" + 5L;
        String locationDelimiterValue = "L" + 20L;
        String barcodeValue = String.join("", datePrefix, assetDelimiterValue,
            assetTypeDelimiterValue,
            locationDelimiterValue);

        System.out.println("barcodeValue: " + barcodeValue);
        Assertions.assertNotNull(barcodeValue);
        Assertions.assertEquals(formatter.format(LocalDate.now()) + "A1T5L20", barcodeValue);
    }
}