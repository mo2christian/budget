package com.mo2christian.line;

public enum LineType {

    CREDIT('c', "Crédit"), DEBIT('d', "Débit");

    private final char value;

    private final String label;

    LineType(char value, String label){
        this.value = value;
        this.label = label;
    }

    public static LineType parse(String value){
        if (value == null || value.isEmpty())
            throw new IllegalArgumentException();
        switch (value.charAt(0)){
            case 'c':
                return LineType.CREDIT;
            case 'd':
                return LineType.DEBIT;
            default:
                    throw new IllegalArgumentException();
        }
    }

    public char value(){
        return value;
    }

    public String label(){
        return label;
    }
}
