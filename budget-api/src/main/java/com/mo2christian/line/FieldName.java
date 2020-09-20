package com.mo2christian.line;

import java.util.stream.Stream;

public enum FieldName {

    LABEL("label"),
    AMOUNT("amount"),
    TYPE("type"),
    FREQUENCY("frequency"),
    BEGIN_DATE("begin_date"),
    END_DATE("end_date"),
    WITHDRAWAL_DAY("withdrawal_day");

    private String name;

    private FieldName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static final FieldName toFieldName(String name){
        return Stream.of(FieldName.values())
                .filter(field -> field.name.equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
