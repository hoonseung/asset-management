package com.sewon.account.converter;

import com.sewon.account.constant.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {


    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return Objects.nonNull(role) ? role.getValue() : null;
    }

    @Override
    public Role convertToEntityAttribute(Integer value) {
        return Objects.nonNull(value) ? Role.fromValue(value) : null;
    }
}
