package com.sewon.common.converter;

import com.sewon.common.constant.NationType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class NationTypeConverter implements AttributeConverter<NationType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NationType nationType) {
        return Objects.nonNull(nationType) ? nationType.getValue() : null;
    }

    @Override
    public NationType convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? NationType.fromValue(value) : null;
    }

}


