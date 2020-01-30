package com.mo2christian.budget;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class LineDto implements Serializable {

    private long id;

    @NotBlank(message = "Label is required")
    private String label;

    @NotNull(message = "Amount is required")
    @Min(value = 0,message = "Min value is 0")
    private BigDecimal amount;

    @NotNull(message = "Type is required")
    private LineType type;

    public LineDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LineType getType() {
        return type;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    public boolean isDebit(){
        return LineType.DEBIT.equals(type);
    }

    public boolean isCredit(){
        return LineType.CREDIT.equals(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineDto lineDto = (LineDto) o;
        return id == lineDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
