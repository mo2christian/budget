package com.mo2christian.common.converter;

import com.mo2christian.common.TemplateFormatter;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class DateParamConverter implements ParamConverter<LocalDate> {

    @Override
    public LocalDate fromString(String param) {
        if (param == null || param.isEmpty())
            return null;
        return LocalDate.parse(param.trim(), DateTimeFormatter.ofPattern(TemplateFormatter.DATE_VALUE_PATTERN));
    }

    @Override
    public String toString(LocalDate date) {
        if (date == null)
            return "";
        return date.format(DateTimeFormatter.ofPattern(TemplateFormatter.DATE_VALUE_PATTERN));
    }

}
