package com.mo2christian.common.converter;

import com.mo2christian.line.FieldConverter;
import com.mo2christian.line.FieldName;
import com.mo2christian.line.LineType;

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

    private ParamConverter<FieldConverter> fieldNameParamConverter;

    private ParamConverter<LineType> lineTypeParamConverter;

    @Inject
    public ConverterProvider(ParamConverter<Date> dateParamConverter,
                             ParamConverter<FieldConverter> fieldNameParamConverter,
                             ParamConverter<LineType> lineTypeParamConverter) {
        this.dateParamConverter = dateParamConverter;
        this.fieldNameParamConverter = fieldNameParamConverter;
        this.lineTypeParamConverter = lineTypeParamConverter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.isAssignableFrom(Date.class)) {
            return (ParamConverter<T>) dateParamConverter;
        }
        if (rawType.isAssignableFrom(FieldConverter.class)) {
            return (ParamConverter<T>) fieldNameParamConverter;
        }
        if (rawType.isAssignableFrom(LineType.class)) {
            return (ParamConverter<T>) lineTypeParamConverter;
        }
        return null;
    }
}
