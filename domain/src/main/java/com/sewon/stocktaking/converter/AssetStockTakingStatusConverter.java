package com.sewon.stocktaking.converter;

import com.sewon.stocktaking.constant.AssetStockTakingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import java.util.Objects;

@Convert
public class AssetStockTakingStatusConverter implements
    AttributeConverter<AssetStockTakingStatus, Integer> {


    @Override
    public Integer convertToDatabaseColumn(AssetStockTakingStatus status) {
        return Objects.nonNull(status) ? status.getValue() : null;
    }

    @Override
    public AssetStockTakingStatus convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? AssetStockTakingStatus.fromValue(value) : null;
    }
}
