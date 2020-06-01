package com.mo2christian.line;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class LineService {

    private EntityManager em;

    @Inject
    public LineService(EntityManager em) {
        this.em = em;
    }

    public void add(@Valid Line line){
        //mettre la date de début au 01
        Calendar cal = new GregorianCalendar();
        cal.setTime(line.getBeginPeriod());
        cal.set(Calendar.DAY_OF_MONTH, 1);

        //mettre la date de fin à la fin du mois
        line.setBeginPeriod(cal.getTime());
        if (line.getEndPeriod() != null){
            cal.setTime(line.getEndPeriod());
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            line.setEndPeriod(cal.getTime());
        }
        em.persist(line);
    }

    public void delete(@Valid Line line){
        em.remove(line);
    }

    public Optional<Line> get(long id){
        Line line = em.find(Line.class, id);
        return Optional.ofNullable(line);
    }

    public List<Line> getAll(){
        return em.createNamedQuery(Line.FIND_ALL, Line.class)
                .getResultList();
    }

    public List<Line> get(Date date){
        return em.createNamedQuery(Line.FIND_ALL, Line.class)
                .getResultList()
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
}
