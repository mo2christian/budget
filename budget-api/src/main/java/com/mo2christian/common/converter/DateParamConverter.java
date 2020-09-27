package com.mo2christian.common.converter;

import com.mo2christian.common.TemplateFormatter;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.ParamConverter;
import java.util.Date;

@ApplicationScoped
public class DateParamConverter implements ParamConverter<Date> {

    @Override
    public Date fromString(String param) {
        try {
            if (param == null || param.isEmpty())
                return null;
            return TemplateFormatter.toDate(param.trim());
        } catch (RuntimeException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public String toString(Date date) {
        if (date == null)
            return "";
        return TemplateFormatter.value(date);
    }

}
