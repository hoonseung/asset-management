package com.sewon.asset.converter;

import com.sewon.asset.constant.AssetDivision;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class AssetDivisionConverter implements AttributeConverter<AssetDivision, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AssetDivision assetDivision) {
        return Objects.nonNull(assetDivision) ? assetDivision.getValue() : null;
    }

    @Override
    public AssetDivision convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? AssetDivision.fromValue(value) : null;
    }
}
