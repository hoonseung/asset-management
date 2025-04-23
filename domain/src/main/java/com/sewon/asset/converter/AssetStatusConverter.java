package com.sewon.asset.converter;

import com.sewon.asset.constant.AssetStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class AssetStatusConverter implements AttributeConverter<AssetStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AssetStatus assetStatus) {
        return Objects.nonNull(assetStatus) ? assetStatus.getValue() : null;
    }

    @Override
    public AssetStatus convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? AssetStatus.fromValue(value) : null;
    }
}
