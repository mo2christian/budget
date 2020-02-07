package com.mo2christian.budget.converter;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.ParamConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
public class DateParamConverter implements ParamConverter<Date> {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_VALUE_PATTERN = "MMM yyyy";

    @Override
    public Date fromString(String param) {
        try {
            if (param == null || param.isEmpty())
                return null;
            return new SimpleDateFormat(DATE_PATTERN).parse(param.trim());
        } catch (ParseException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public String toString(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_PATTERN).format(date);
    }

    public String toStringLabel(Date date){
        if (date == null)
            return "";
        return new SimpleDateFormat(DATE_VALUE_PATTERN).format(date);
    }
}
