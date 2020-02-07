package com.mo2christian.budget;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BudgetDto implements Serializable {

    private List<LineDto> lines;

    private BigDecimal balance;

    public BudgetDto() {
        lines = new ArrayList<>();
        balance = new BigDecimal(0);
    }

    public List<LineDto> getLines() {
        return lines;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void add(LineDto dto){
        lines.add(dto);
        if (LineType.CREDIT.equals(dto.getType())) {
            balance = balance.add(dto.getAmount());
        }
        else {
            balance = balance.subtract(dto.getAmount());
        }
    }

}
