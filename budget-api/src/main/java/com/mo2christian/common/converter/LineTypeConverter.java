package com.mo2christian.common.converter;

import com.mo2christian.line.LineType;
import io.quarkus.qute.TemplateExtension;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ext.ParamConverter;

@ApplicationScoped
@TemplateExtension
public class LineTypeConverter implements ParamConverter<LineType> {
    @Override
    public LineType fromString(String s) {
        return LineType.toLineType(s);
    }

    @Override
    public String toString(LineType lineType) {
        return String.valueOf(lineType.value());
    }

    public static String label(LineType lineType){
        return lineType.label();
    }
}
