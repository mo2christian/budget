package com.mo2christian.budget;

import com.mo2christian.budget.converter.DateParamConverter;

public class LineMapper {

    private final DateParamConverter converter;

    public LineMapper(DateParamConverter converter){
        this.converter = converter;
    }

    public Line toLine(LineDto dto){
        Line line = new Line();
        line.setAmount(dto.getAmount());
        line.setLabel(dto.getLabel());
        line.setType(dto.getType());
        line.setFrequency(dto.getFrequency());
        line.setBeginPeriod(dto.getBeginPeriod());
        line.setEndPeriod(dto.getEndPeriod());
        return line;
    }

    public LineDto toDto(Line line){
        LineDto dto = new LineDto();
        dto.setAmount(line.getAmount());
        dto.setId(line.getId());
        dto.setLabel(line.getLabel());
        dto.setType(line.getType());
        dto.setFrequency(line.getFrequency());
        dto.setBeginPeriod(line.getBeginPeriod());
        dto.setEndPeriod(line.getEndPeriod());
        dto.setBeginPeriodFormat(converter.toStringLabel(line.getBeginPeriod()));
        dto.setEndPeriodFormat(converter.toStringLabel(line.getEndPeriod()));
        return dto;
    }

}
