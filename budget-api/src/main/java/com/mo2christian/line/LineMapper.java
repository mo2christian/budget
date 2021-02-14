package com.mo2christian.line;

public class LineMapper {

    public LineMapper(){
    }

    public Line toLine(LineDto dto){
        Line line = new Line();
        line.setAmount(dto.getAmount());
        line.setLabel(dto.getLabel());
        line.setType(dto.getType());
        line.setFrequency(dto.getFrequency());
        line.setBeginPeriod(dto.getBeginPeriod());
        line.setEndPeriod(dto.getEndPeriod());
        line.setWithdrawalDay(dto.getWithdrawalDay());
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
        dto.setWithdrawalDay(line.getWithdrawalDay());
        return dto;
    }

}
