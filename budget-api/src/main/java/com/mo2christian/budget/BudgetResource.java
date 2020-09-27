package com.mo2christian.budget;

import com.mo2christian.common.converter.DateParamConverter;
import com.mo2christian.line.LineMapper;
import com.mo2christian.line.LineService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Path("/budget")
public class BudgetResource {

    private final LineService lineService;

    @Inject
    Template budget;

    @Inject
    public BudgetResource(LineService lineService) {
        this.lineService = lineService;
    }

    @GET
    @Path("/{period}")
    public TemplateInstance index(@PathParam("period") Date date){
        return build(date);
    }

    @GET
    public TemplateInstance index(){
        return build(new Date());
    }

    private TemplateInstance build(Date date){
        Calendar next = new GregorianCalendar();
        next.setTime(date);
        next.add(Calendar.MONTH, 1);
        Calendar previous = new GregorianCalendar();
        previous.setTime(date);
        previous.add(Calendar.MONTH, -1);
        BudgetDto budgetDto = new BudgetDto();
        LineMapper mapper = new LineMapper();
        lineService.get(date)
                .stream()
                .map(mapper::toDto)
                .forEach(budgetDto::add);
        return budget.data("balance", budgetDto.getBalance())
                .data("lines", budgetDto.getLines())
                .data("previous", previous.getTime())
                .data("next", next.getTime())
                .data("now", date)
                .data("actualBalance", budgetDto.getActualBalance());
    }

}
