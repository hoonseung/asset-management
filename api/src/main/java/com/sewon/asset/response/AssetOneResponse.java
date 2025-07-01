package com.sewon.asset.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sewon.asset.dto.AssetResult;
import java.time.LocalDate;

@JsonInclude(Include.NON_EMPTY)
public record AssetOneResponse(

    Long id,
    String barcode,
    String corporation,
    String department,
    String location,
    String division,
    String parentCategory,
    String childCategory,
    String status,
    String manufacturer,
    String model,
    LocalDate acquisitionDate,
    Integer acquisitionPrice,
    String registerName

) {

    public static AssetOneResponse from(AssetResult asset) {
        return new AssetOneResponse(
            asset.id(),
            asset.barcode(),
            asset.corporation(),
            asset.department(),
            asset.location(),
            asset.division(),
            asset.parentCategory(),
            asset.childCategory(),
            asset.status(),
            asset.manufacturer(),
            asset.model(),
            asset.acquisitionDate(),
            asset.acquisitionPrice(),
            asset.registerName()
        );
    }
}
