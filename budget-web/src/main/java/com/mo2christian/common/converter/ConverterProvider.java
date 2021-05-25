package com.mo2christian.common.converter;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

@Provider
@ApplicationScoped
public class ConverterProvider implements ParamConverterProvider {

    private final ParamConverter<LocalDate> dateParamConverter;

    @Inject
    public ConverterProvider(ParamConverter<LocalDate> dateParamConverter) {
        this.dateParamConverter = dateParamConverter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.isAssignableFrom(LocalDate.class)) {
            return (ParamConverter<T>) dateParamConverter;
        }
        return null;
    }
}
