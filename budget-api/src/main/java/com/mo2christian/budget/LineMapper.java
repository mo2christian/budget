package com.mo2christian.budget;

public class LineMapper {

    public static LineMapper INSTANCE = new LineMapper();

    private LineMapper(){}

    public Line toLine(LineDto dto){
        Line line = new Line();
        line.setAmount(dto.getAmount());
        line.setLabel(dto.getLabel());
        line.setType(dto.getType());
        return line;
    }

    public LineDto toDto(Line line){
        LineDto dto = new LineDto();
        dto.setAmount(line.getAmount());
        dto.setId(line.getId());
        dto.setLabel(line.getLabel());
        dto.setType(line.getType());
        return dto;
    }

}
