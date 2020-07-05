package com.mo2christian.line;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.ext.ParamConverter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class LineService {

    private ParamConverter<Date> dateParamConverter;

    private LineRepository repository;

    @Inject
    public LineService(LineRepository repository, ParamConverter<Date> dateParamConverter) {
        this.repository = repository;
        this.dateParamConverter = dateParamConverter;
    }

    public void add(@Valid Line line){
        //mettre la date de début au 01
        line.setBeginPeriod(startDate(line.getBeginPeriod()));

        //mettre la date de fin à la fin du mois
        if (line.getEndPeriod() != null)
            line.setEndPeriod(endDate(line.getEndPeriod()));

        line.setId(repository.count() + 1);
        repository.persist(line);
    }

    public void update(Field field){
        Line line = get(field.getPk())
                .orElseThrow(IllegalArgumentException::new);
        switch (field.getName()){
            case LABEL:
                line.setLabel(field.getValue());
                break;
            case AMOUNT:
                line.setAmount(BigDecimal.valueOf(Double.valueOf(field.getValue())));
                break;
            case TYPE:
                line.setType(LineType.toLineType(field.getValue()));
                break;
            case FREQUENCY:
                line.setFrequency(Integer.valueOf(field.getValue()));
                break;
            case BEGIN_DATE:
                line.setBeginPeriod(startDate(dateParamConverter.fromString(field.getValue())));
                break;
            case END_DATE:
                line.setEndPeriod(endDate(dateParamConverter.fromString(field.getValue())));
        }
        repository.update(line);
    }

    public void delete(@Valid Line line){
        repository.delete(line);
    }

    public Optional<Line> get(long id){
        Line line = repository.findById(id);
        return Optional.ofNullable(line);
    }

    public List<Line> getAll(){
        return repository.listAll();
    }

    public List<Line> get(Date date){
        return repository.findAll()
                .list()
                .stream()
                .filter(l -> l.getBeginPeriod().before(date))
                .filter(l -> l.getEndPeriod() == null || date.before(l.getEndPeriod()))
                .filter(l -> {
                    LocalDate begin = toLocalDate(l.getBeginPeriod());
                    LocalDate end = l.getEndPeriod() == null ? toLocalDate(date) : toLocalDate(l.getEndPeriod());
                    Period period = Period.between(begin, end);
                    long interval = period.getMonths() + 12 * period.getYears();
                    return interval % l.getFrequency() == 0;
                })
                .collect(Collectors.toList());
    }

    private LocalDate toLocalDate(Date date){
        if (date instanceof java.sql.Date)
            return ((java.sql.Date)date).toLocalDate();
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private Date startDate(Date date){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    private Date endDate(Date date){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
}
