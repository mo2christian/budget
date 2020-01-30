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

    public void setLines(List<LineDto> lines) {
        this.lines = lines;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
