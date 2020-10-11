package com.mo2christian.line;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Line {

    @NotNull
    private long id;

    @NotBlank
    private String label;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LineType type;

    @NotNull
    @Min(1)
    private int frequency = 1;

    @NotNull
    private Date beginPeriod;

    private Date endPeriod;

    @Max(30)
    @Min(1)
    private int withdrawalDay = 5;

    public Line() {
        beginPeriod = new Date();
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public int getWithdrawalDay() {
        return withdrawalDay;
    }

    public void setWithdrawalDay(int withdrawalDay) {
        this.withdrawalDay = withdrawalDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return id == line.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
