package com.mo2christian.common;

import com.mo2christian.line.LineType;
import io.quarkus.qute.TemplateExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@TemplateExtension
public class TemplateFormatter {

    public static final String DATE_VALUE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_LABEL_PATTERN = "MMM yyyy";

    public static String label(LineType lineType){
        return lineType.label();
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

    public static String label(Integer frequency){
        if (frequency == 1)
            return "Each month";
        if (frequency == 2)
            return "Each Bi-month";
        if (frequency == 3)
            return "Each trimester";
        if (frequency == 6)
            return "Each semester";
        if (frequency == 12)
            return "Each year";
        return String.format("Each %d months", frequency);
    }

    public static Date toDate(String value){
        try{
            if (value == null)
                throw new IllegalArgumentException("Invalid date");
            return new SimpleDateFormat(DATE_VALUE_PATTERN).parse(value.trim());
        }
        catch(ParseException ex){
            throw new RuntimeException(ex);
        }
    }

}
