package com.mo2christian.budget;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = Line.FIND_ALL, query = "SELECT l FROM Line l")
})
@Table(name = "line")
public class Line {

    public static final String FIND_ALL="Line.findAll";

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(generator = "line_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "line_seq", sequenceName = "line_seq", allocationSize = 1)
    private long id;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @AttributeOverride(name = "value", column = @Column(name = "type", nullable = false))
    @Embedded
    private LineType type;

    public Line() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LineType getType() {
        return type;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return id == line.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
