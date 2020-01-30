package com.mo2christian.budget;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LineService {

    private EntityManager em;

    @Inject
    public LineService(EntityManager em) {
        this.em = em;
    }

    public void add(@Valid Line line){
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

}
