package com.mo2christian.line;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import java.util.Objects;

public class Field {

    @NotNull
    @FormParam("name")
    private FieldName name;

    @NotNull()
    @FormParam("pk")
    private Long pk;

    @NotNull
    @FormParam("value")
    private String value;

    public Field() {
    }

    public FieldName getName() {
        return name;
    }

    public void setName(FieldName name) {
        this.name = name;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(pk, field.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", id=" + pk +
                ", value='" + value + '\'' +
                '}';
    }
}
