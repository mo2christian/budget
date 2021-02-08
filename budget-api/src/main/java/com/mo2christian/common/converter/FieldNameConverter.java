package com.mo2christian.common.converter;

import com.mo2christian.line.FieldConverter;
import com.mo2christian.line.FieldName;
import com.mo2christian.line.Line;
import com.mo2christian.line.LineType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@ApplicationScoped
public class FieldNameConverter implements ParamConverter<FieldConverter> {

    private final ParamConverter<LocalDate> dateConverter;

    @Inject
    public FieldNameConverter(ParamConverter<LocalDate> dateConverter) {
        this.dateConverter = dateConverter;
    }

    @Override
    public FieldConverter fromString(String s) {
        FieldName fieldName = FieldName.toFieldName(s);
        switch (fieldName){
            case LABEL:
                return label(s);
            case AMOUNT:
                return amount(s);
            case WITHDRAWAL_DAY:
                return withdrawalDay(s);
            case TYPE:
                return type(s);
            case FREQUENCY:
                return frequency(s);
            case BEGIN_DATE:
                return beginDate(s);
            case END_DATE:
                return endDate(s);
        }
        return null;
    }

    @Override
    public String toString(FieldConverter fieldName) {
        return fieldName.getName();
    }

    private FieldConverter<String> label(String name){
        FieldConverter<String> fieldConverter = new FieldConverter<>(name);
        fieldConverter.setConsumer(Line::setLabel);
        return fieldConverter;
    }

    private FieldConverter<BigDecimal> amount(String name){
        FieldConverter<BigDecimal> fieldConverter = new FieldConverter<>(name);
        fieldConverter.setTransform(s -> BigDecimal.valueOf(Double.parseDouble(s)));
        fieldConverter.setConsumer(Line::setAmount);
        return fieldConverter;
    }

    private FieldConverter<LineType> type(String name){
        FieldConverter<LineType> fieldConverter = new FieldConverter<>(name);
        fieldConverter.setTransform(LineType::toLineType);
        fieldConverter.setConsumer(Line::setType);
        return fieldConverter;
    }

    private FieldConverter<Integer> frequency(String name){
        FieldConverter<Integer> fieldConverter = new FieldConverter<>(name);
        fieldConverter.setTransform(Integer::parseInt);
        fieldConverter.setConsumer(Line::setFrequency);
        return fieldConverter;
    }

    private FieldConverter<Integer> withdrawalDay(String name){
        FieldConverter<Integer> fieldConverter = new FieldConverter<>(name);
        fieldConverter.setTransform(Integer::parseInt);
        fieldConverter.setConsumer(Line::setWithdrawalDay);
        return fieldConverter;
    }

    private FieldConverter<LocalDate> beginDate(String name){
        FieldConverter<LocalDate> fieldConverter = new FieldConverter<>(name);
        fieldConverter.setTransform(dateConverter::fromString);
        fieldConverter.setConsumer(Line::setBeginPeriod);
        return fieldConverter;
    }

    private FieldConverter<LocalDate> endDate(String name){
        FieldConverter<LocalDate> fieldConverter = new FieldConverter<>(name);
        fieldConverter.setTransform(dateConverter::fromString);
        fieldConverter.setConsumer(Line::setEndPeriod);
        return fieldConverter;
    }
}
