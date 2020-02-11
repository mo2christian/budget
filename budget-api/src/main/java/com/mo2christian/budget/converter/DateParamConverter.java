package com.mo2christian.budget.converter;

import io.quarkus.qute.TemplateExtension;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.ParamConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
@TemplateExtension
public class DateParamConverter implements ParamConverter<Date> {

    public static final String DATE_VALUE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_LABEL_PATTERN = "MMM yyyy";

    @Override
    public Date fromString(String param) {
        try {
            if (param == null || param.isEmpty())
                return null;
            return new SimpleDateFormat(DATE_VALUE_PATTERN).parse(param.trim());
        } catch (ParseException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public String toString(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_VALUE_PATTERN).format(date);
    }

    public static String label(Date date){
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_LABEL_PATTERN).format(date);
    }

    public static String value(Date date){
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_VALUE_PATTERN).format(date);
    }
}
