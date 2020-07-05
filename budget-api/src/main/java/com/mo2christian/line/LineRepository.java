package com.mo2christian.line;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LineRepository implements PanacheMongoRepositoryBase<Line, Long> {
}
