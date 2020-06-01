package com.mo2christian.common.converter;

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
public class DateParamConverterProvider implements ParamConverterProvider {

    private ParamConverter<Date> dateParamConverter;

    @Inject
    public DateParamConverterProvider(DateParamConverter dateParamConverter) {
        this.dateParamConverter = dateParamConverter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.isAssignableFrom(Date.class)) {
            return (ParamConverter<T>) dateParamConverter;
        }
        return null;
    }
}
