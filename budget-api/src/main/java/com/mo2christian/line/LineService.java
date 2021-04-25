package com.mo2christian.line;

import com.mo2christian.common.TemplateFormatter;
import com.mo2christian.common.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class LineService {

    private final LineRepository repository;

    @Inject
    public LineService(LineRepository repository) {
        this.repository = repository;
    }

    public void add(@Valid Line line){
        //mettre la date de début au 01
        line.setBeginPeriod(line.getBeginPeriod().withDayOfMonth(1));

        //mettre la date de fin à la fin du mois
        if (line.getEndPeriod() != null){
            Calendar cal = new GregorianCalendar();
            cal.setTime(Utils.toDate(line.getBeginPeriod()));
            line.setEndPeriod(line.getEndPeriod()
                    .withDayOfMonth(cal.getActualMaximum(Calendar.DAY_OF_MONTH)));
        }

        repository.persist(line);
    }

    public void update(@Valid Field field){
        Line line = get(field.getPk())
                .orElseThrow(IllegalArgumentException::new);
        FieldName fieldName = field.getName();
        switch (fieldName){
            case LABEL:
                line.setLabel(field.getValue());
                break;
            case AMOUNT:
                line.setAmount(BigDecimal.valueOf(Double.parseDouble(field.getValue())));
                break;
            case WITHDRAWAL_DAY:
                line.setWithdrawalDay(Integer.parseInt(field.getValue()));
                break;
            case TYPE:
                line.setType(LineType.parse(field.getValue()));
                break;
            case FREQUENCY:
                line.setFrequency(Integer.parseInt(field.getValue()));
                break;
            case BEGIN_DATE:
                line.setBeginPeriod(toDate(field.getValue()));
                break;
            case END_DATE:
                line.setEndPeriod(toDate(field.getValue()));
        }
        repository.update(line);
    }

    public void delete(@Valid Line line){
        repository.delete(line);
    }

    public Optional<Line> get(long id){
        return repository.findById(id);
    }

    public List<Line> getAll(){
        return repository.listAll();
    }

    public List<Line> get(final LocalDate date){
        return repository.listAll()
                .stream()
                .filter(l -> l.getBeginPeriod().isBefore(date))
                .filter(l -> l.getEndPeriod() == null || date.isBefore(l.getEndPeriod()))
                .filter(l -> {
                    LocalDate begin = l.getBeginPeriod();
                    LocalDate end = l.getEndPeriod() == null ? date : l.getEndPeriod();
                    Period period = Period.between(begin, end);
                    long interval = period.getMonths() + 12L * period.getYears();
                    return interval % l.getFrequency() == 0;
                })
                .collect(Collectors.toList());
    }

    private LocalDate toDate(String s){
        return LocalDate.parse(s.trim(), DateTimeFormatter.ofPattern(TemplateFormatter.DATE_VALUE_PATTERN));
    }

}
