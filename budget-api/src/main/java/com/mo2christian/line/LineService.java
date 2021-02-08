package com.mo2christian.line;

import com.mo2christian.common.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
        field.getName().set(line, field.getValue());
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
                    long interval = period.getMonths() + 12 * period.getYears();
                    return interval % l.getFrequency() == 0;
                })
                .collect(Collectors.toList());
    }

}
