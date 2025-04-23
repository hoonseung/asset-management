package com.sewon.inbound.converter;

import com.sewon.inbound.constant.InboundType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter
public class InboundTypeConverter implements AttributeConverter<InboundType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InboundType inboundType) {
        return Objects.nonNull(inboundType) ? inboundType.getValue() : null;
    }

    @Override
    public InboundType convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? InboundType.fromValue(value) : null;
    }
}
