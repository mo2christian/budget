package com.mo2christian.line;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import static com.mo2christian.common.Utils.*;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LineRepository {

    private final String KIND = "Line";
    private final Datastore datastore;

    @Inject
    public LineRepository(Datastore datastore){
        this.datastore = datastore;
    }

    @CacheInvalidateAll(cacheName = "line")
    public void persist(Line line){
        IncompleteKey key = datastore.newKeyFactory()
                .setKind(KIND)
                .newKey();
        datastore.add(toEntity(line, key));
    }

    @CacheInvalidateAll(cacheName = "line")
    public void update(Line line){
        Key key = datastore.newKeyFactory()
                .setKind(KIND)
                .newKey(line.getId());
        datastore.put(toEntity(line, key));
    }

    @CacheResult(cacheName = "line")
    public List<Line> listAll(){
        StructuredQuery<Entity> query = Query.newEntityQueryBuilder().setKind(KIND).build();
        QueryResults<Entity> results = datastore.run(query);
        List<Line> entities = new ArrayList<>();
        while (results.hasNext()) {
            Entity result = results.next();
            entities.add(toLine(result));
        }
        return entities;
    }

    @CacheInvalidateAll(cacheName = "line")
    public void delete(Line line) {
        Key key = datastore.newKeyFactory()
                .setKind(KIND)
                .newKey(line.getId());
        datastore.delete(key);
    }

    @CacheResult(cacheName = "line")
    public Optional<Line> findById(long id) {
        Key key = datastore.newKeyFactory()
                .setKind(KIND)
                .newKey(id);
        Entity entity = datastore.get(key);
        if (entity == null)
            return Optional.empty();
        return Optional.of(toLine(entity));
    }

    private Line toLine(Entity entity){
        Line line = new Line();
        line.setType(LineType.toLineType(toString(entity.getLong("type"))));
        line.setId(entity.getKey().getId());
        line.setFrequency((int)entity.getLong("frequency"));
        line.setBeginPeriod(toLocalDate(entity.getTimestamp("beginDate").toDate()));
        if (entity.contains("endDate")){
            line.setEndPeriod(toLocalDate(entity.getTimestamp("endDate").toDate()));
        }
        line.setLabel(entity.getString("label"));
        line.setWithdrawalDay((int)entity.getLong("withdrawalDay"));
        line.setAmount(BigDecimal.valueOf(entity.getDouble("amount")));
        return line;
    }

    private String toString(Long l){
        return Character.toString((char)l.intValue());
    }

    private FullEntity<IncompleteKey> toEntity(Line line, IncompleteKey key){
        BaseEntity.Builder<IncompleteKey, FullEntity.Builder<IncompleteKey>> builder = Entity.newBuilder(key)
                .set("label", line.getLabel())
                .set("frequency", line.getFrequency())
                .set("amount", line.getAmount().doubleValue())
                .set("withdrawalDay", line.getWithdrawalDay())
                .set("beginDate", Timestamp.of(toDate(line.getBeginPeriod())))
                .set("type", line.getType().value());
        if (line.getEndPeriod() != null){
            builder.set("endDate", Timestamp.of(toDate(line.getEndPeriod())));
        }
        return (FullEntity<IncompleteKey>)builder.build();

    }

}
