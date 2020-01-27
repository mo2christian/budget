package com.mo2christian.budget;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LineType implements Serializable {

    private static final char D_VAL = 'd';
    private static final char C_VAL = 'c';

    public static final LineType DEBIT = new LineType(D_VAL);
    public static final LineType CREDIT = new LineType(C_VAL);

    private char value;

    private LineType(){}

    private LineType(char value){
        this.value = value;
    }

    public char getValue(){
        return value;
    }

    public static LineType valueOf(String value){
        value = value.trim();
        if (value.length() != 1)
            throw new IllegalArgumentException(String.format("Invalid type of LineType : %s", value));
        switch (value.charAt(0)){
            case C_VAL:
                return LineType.CREDIT;
            case D_VAL:
                return LineType.DEBIT;
            default:
                throw new IllegalArgumentException(String.format("Invalid type of LineType : %s", value));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineType lineType = (LineType) o;
        return value == lineType.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
