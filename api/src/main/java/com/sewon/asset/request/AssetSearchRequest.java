package com.sewon.asset.request;

import com.sewon.asset.dto.AssetSearchProperties;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Setter
@Getter
public class AssetSearchRequest {

    private String locationId;
    private String parentTypeId;
    private String childTypeId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate after;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate before;

    private int size;


    public AssetSearchProperties toAssetSearchProperties() {
        return AssetSearchProperties.of(
            locationId != null ? Long.valueOf(locationId) : null,
            parentTypeId != null ? Long.valueOf(parentTypeId) : null,
            childTypeId != null ? Long.valueOf(childTypeId) : null,
            after,
            before,
            size
        );
    }

}

