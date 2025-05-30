package com.sewon.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean isRead) {
        return Boolean.TRUE.equals(isRead) ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer value) {
        return value.equals(1) ? Boolean.TRUE : Boolean.FALSE;
    }
}
