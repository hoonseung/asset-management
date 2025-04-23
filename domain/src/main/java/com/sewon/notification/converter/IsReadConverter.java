package com.sewon.notification.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter
public class IsReadConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean isRead) {
        return Objects.nonNull(isRead) ? isRead ? 1 : 0 : null;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? value.equals(1) ? Boolean.TRUE
            : value.equals(0) ? Boolean.FALSE : null : null;
    }
}
