package com.sewon.asset.request;

import com.sewon.asset.dto.properties.AssetSearchProperties;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Setter
@Getter
public class AssetSearchRequest {

    private String corporationId;
    private String affiliationId;
    private String locationId;
    private String parentTypeId;
    private String childTypeId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate after;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate before;

    private int size = 10000;


    public AssetSearchProperties toAssetSearchProperties() {
        return AssetSearchProperties.of(
            corporationId != null ? Long.valueOf(corporationId) : null,
            affiliationId != null ? Long.valueOf(affiliationId) : null,
            locationId != null ? Long.valueOf(locationId) : null,
            parentTypeId != null ? Long.valueOf(parentTypeId) : null,
            childTypeId != null ? Long.valueOf(childTypeId) : null,
            after,
            before,
            size
        );
    }

}

