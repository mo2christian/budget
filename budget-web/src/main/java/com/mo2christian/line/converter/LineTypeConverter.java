package com.mo2christian.line.converter;

import com.mo2christian.line.LineType;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ext.ParamConverter;

@ApplicationScoped
public class LineTypeConverter implements ParamConverter<LineType> {
    @Override
    public LineType fromString(String s) {
        return LineType.parse(s);
    }

    @Override
    public String toString(LineType lineType) {
        return String.valueOf(lineType.value());
    }

}
