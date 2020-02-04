package com.mo2christian.budget;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = Line.FIND_ALL, query = "SELECT l FROM Line l")
})
@Table(name = "line")
@Cacheable
public class Line {

    public static final String FIND_ALL="Line.findAll";

    @NotNull
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(generator = "line_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "line_seq", sequenceName = "line_seq", allocationSize = 1)
    private long id;

    @NotBlank
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @NotNull
    @AttributeOverride(name = "value", column = @Column(name = "type", nullable = false))
    @Embedded
    private LineType type;

    private int frequency;

    private String beginPeriod;

    private String endPeriod;

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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getBeginPeriod() {
        return beginPeriod;
    }

    public void setBeginPeriod(String beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
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
