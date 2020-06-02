package com.mo2christian.common.converter;

import com.mo2christian.line.FieldName;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

@Provider
@ApplicationScoped
public class ConverterProvider implements ParamConverterProvider {

    private ParamConverter<Date> dateParamConverter;

    private ParamConverter<FieldName> fieldNameParamConverter;

    @Inject
    public ConverterProvider(DateParamConverter dateParamConverter, FieldNameConverter fieldNameParamConverter) {
        this.dateParamConverter = dateParamConverter;
        this.fieldNameParamConverter = fieldNameParamConverter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.isAssignableFrom(Date.class)) {
            return (ParamConverter<T>) dateParamConverter;
        }
        if (rawType.isAssignableFrom(FieldName.class)) {
            return (ParamConverter<T>) fieldNameParamConverter;
        }
        return null;
    }
}
