package com.sewon.outbound.converter;

import com.sewon.outbound.constant.OutboundType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter
public class OutboundTypeConverter implements AttributeConverter<OutboundType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OutboundType outboundType) {
        return Objects.nonNull(outboundType) ? outboundType.getValue() : null;
    }

    @Override
    public OutboundType convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? OutboundType.fromValue(value) : null;
    }
}
