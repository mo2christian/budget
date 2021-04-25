package com.mo2christian.line.converter;

import com.mo2christian.line.FieldName;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ext.ParamConverter;

@ApplicationScoped
public class FieldNameConverter implements ParamConverter<FieldName> {

    public FieldNameConverter() {
    }

    @Override
    public FieldName fromString(String s) {
        return FieldName.parse(s);
    }

    @Override
    public String toString(FieldName fieldName) {
        return fieldName.getName();
    }
}
