package com.mo2christian.budget;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.math.BigDecimal;

@Path("/")
public class BudgetResource {

    private LineService lineService;

    @Inject
    Template budget;

    @Inject
    public BudgetResource(LineService lineService) {
        this.lineService = lineService;
    }

    @GET
    @RolesAllowed("user")
    public TemplateInstance index(){
        BudgetDto budgetDto = new BudgetDto();
        lineService.getAll()
                .stream()
                .map(LineMapper.INSTANCE::toDto)
                .forEach(dto -> {
                    budgetDto.getLines().add(dto);
                    if (LineType.CREDIT.equals(dto.getType())) {
                        BigDecimal b = budgetDto.getBalance().add(dto.getAmount());
                        budgetDto.setBalance(b);
                    }
                    else {
                        BigDecimal b = budgetDto.getBalance().subtract(dto.getAmount());
                        budgetDto.setBalance(b);
                    }
                });
        return budget.data("balance", budgetDto.getBalance())
                .data("lines", budgetDto.getLines());
    }


}
