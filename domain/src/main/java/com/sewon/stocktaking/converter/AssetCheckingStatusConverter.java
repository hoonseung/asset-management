package com.sewon.stocktaking.converter;

import com.sewon.stocktaking.constant.AssetCheckingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import java.util.Objects;

@Convert
public class AssetCheckingStatusConverter implements
    AttributeConverter<AssetCheckingStatus, Integer> {


    @Override
    public Integer convertToDatabaseColumn(AssetCheckingStatus status) {
        return Objects.nonNull(status) ? status.getValue() : null;
    }

    @Override
    public AssetCheckingStatus convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? AssetCheckingStatus.fromValue(value) : null;
    }
}
