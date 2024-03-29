
package com.mo2christian.line;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class LineDto implements Serializable {

    private long id;

    @FormParam("label")
    @NotBlank(message = "Label is required")
    private String label;

    @FormParam("amount")
    @NotNull(message = "Amount is required")
    @Min(value = 0,message = "Min value is 0")
    private BigDecimal amount;

    @FormParam("type")
    @NotNull(message = "Type is required")
    private LineType type;

    @FormParam("frequency")
    @NotNull(message = "Frequency is null")
    @Min(message = "Frequency must be greather than zero", value = 1)
    private int frequency = 1;

    @FormParam("beginPeriod")
    @NotNull(message = "Begin period is required")
    private LocalDate beginPeriod = LocalDate.now();

    @FormParam("endPeriod")
    private LocalDate endPeriod;

    @Min(1)
    @Max(30)
    @FormParam("withdrawalDay")
    private int withdrawalDay;

    private boolean achieved = false;

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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public LocalDate getBeginPeriod() {
        return beginPeriod;
    }

    public void setBeginPeriod(LocalDate beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }

    public int getWithdrawalDay() {
        return withdrawalDay;
    }

    public void setWithdrawalDay(int withdrawalDay) {
        this.withdrawalDay = withdrawalDay;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
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
