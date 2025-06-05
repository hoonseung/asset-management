package com.sewon.rental.converter;

import com.sewon.rental.constant.RentalStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter
public class RentalStatusConverter implements AttributeConverter<RentalStatus, Integer> {


    @Override
    public Integer convertToDatabaseColumn(RentalStatus rentalStatus) {
        return Objects.nonNull(rentalStatus) ? rentalStatus.getValue() : null;
    }

    @Override
    public RentalStatus convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? RentalStatus.fromValue(value) : null;
    }
}
