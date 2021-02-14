package com.mo2christian.budget;

import com.mo2christian.line.LineDto;
import com.mo2christian.line.LineType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class BudgetDto implements Serializable {

    private final List<LineDto> lines;

    private final BigDecimal balance;

    private BigDecimal billToPay;

    private BigDecimal billPayed;

    private BigDecimal actualBalance;

    public BudgetDto() {
        lines = new ArrayList<>();
        balance = new BigDecimal(0);
        billToPay = new BigDecimal(0);
        billPayed = new BigDecimal(0);
        actualBalance = new BigDecimal(0);
    }

    public BigDecimal getBillToPay() {
        return billToPay;
    }

    public BigDecimal getBillPayed() {
        return billPayed;
    }

    public List<LineDto> getLines() {
        return lines;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void add(LineDto dto){
        lines.add(dto);
        boolean achieved = isAchieved(dto);
        dto.setAchieved(achieved);
        if (LineType.DEBIT == dto.getType()) {
            if (achieved) {
                billPayed = billPayed.add(dto.getAmount());
                actualBalance = actualBalance.subtract(dto.getAmount());
            } else {
                billToPay = billToPay.add(dto.getAmount());
            }
        }
        else {
            actualBalance  = actualBalance.add(dto.getAmount());
        }
    }

    private boolean isAchieved(LineDto line){
        LocalDate now = LocalDate.now();
        return prelevDate(line) <= now.getDayOfMonth();
    }

    private int prelevDate(LineDto line){
        LocalDate prelev = LocalDate.now()
                .withDayOfMonth(line.getWithdrawalDay());
        if (prelev.getDayOfWeek() == DayOfWeek.SATURDAY){
            return line.getWithdrawalDay() + 2;
        }
        if (prelev.getDayOfWeek() == DayOfWeek.SUNDAY){
            return line.getWithdrawalDay() + 1;
        }
        return line.getWithdrawalDay();
    }

}
