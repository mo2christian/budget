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
            if (isAchived(dto))
                actualBalance = actualBalance.add(dto.getAmount());
        }
        else {
            balance = balance.subtract(dto.getAmount());
            if (isAchived(dto))
                actualBalance = actualBalance.subtract(dto.getAmount());
        }
    }

    private boolean isAchived(LineDto line){
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int dayOfMount = now.get(Calendar.DAY_OF_MONTH);
        return line.getWithdrawalDay() <= dayOfMount;
    }

}
