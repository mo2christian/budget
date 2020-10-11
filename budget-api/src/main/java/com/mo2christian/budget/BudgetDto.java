package com.mo2christian.budget;

import com.mo2christian.line.LineDto;
import com.mo2christian.line.LineType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class BudgetDto implements Serializable {

    private List<LineDto> lines;

    private BigDecimal balance;

    private BigDecimal actualBalance;

    public BudgetDto() {
        lines = new ArrayList<>();
        balance = new BigDecimal(0);
        actualBalance = new BigDecimal(0);
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
        if (LineType.CREDIT.equals(dto.getType())) {
            balance = balance.add(dto.getAmount());
            if (isAchieved(dto)) {
                dto.setAchieved(true);
                actualBalance = actualBalance.add(dto.getAmount());
            }
        }
        else {
            balance = balance.subtract(dto.getAmount());
            if (isAchieved(dto)) {
                dto.setAchieved(true);
                actualBalance = actualBalance.subtract(dto.getAmount());
            }
        }
    }

    private boolean isAchieved(LineDto line){
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int dayOfMount = now.get(Calendar.DAY_OF_MONTH);
        return prelevDate(line) <= dayOfMount;
    }

    private int prelevDate(LineDto line){
        Calendar prelevDate = new GregorianCalendar();
        prelevDate.setTime(new Date());
        prelevDate.set(Calendar.DAY_OF_MONTH, line.getWithdrawalDay());
        int day = prelevDate.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.SATURDAY){
            return line.getWithdrawalDay() + 2;
        }
        if (day == Calendar.SUNDAY){
            return line.getWithdrawalDay() + 1;
        }
        return line.getWithdrawalDay();
    }

}
