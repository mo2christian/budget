package com.mo2christian.line.converter;

import com.mo2christian.line.FieldName;
import com.mo2christian.line.LineType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@ApplicationScoped
public class LineConverterProvider implements ParamConverterProvider {

    private final ParamConverter<LineType> lineTypeParamConverter;
    private final ParamConverter<FieldName> fieldNameParamConverter;

    @Inject
    public LineConverterProvider(ParamConverter<LineType> lineTypeParamConverter,
                                 ParamConverter<FieldName> fieldNameParamConverter) {
        this.lineTypeParamConverter = lineTypeParamConverter;
        this.fieldNameParamConverter = fieldNameParamConverter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.isAssignableFrom(LineType.class)) {
            return (ParamConverter<T>) lineTypeParamConverter;
        }
        if (rawType.isAssignableFrom(FieldName.class)) {
            return (ParamConverter<T>) fieldNameParamConverter;
        }
        return null;
    }
}
