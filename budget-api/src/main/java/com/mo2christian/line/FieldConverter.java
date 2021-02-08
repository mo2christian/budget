package com.mo2christian.line;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class FieldConverter<T> {

    private final String name;

    private Function<String, T> transform;

    private BiConsumer<Line, T> consumer;

    public FieldConverter(String name){
        this.name = name;
        transform = (String s) -> (T) s;
    }

    public String getName() {
        return name;
    }

    public final void setTransform(Function<String, T> transform){
        this.transform = transform;
    }

    public final void setConsumer(BiConsumer<Line, T> consumer){
        this.consumer = consumer;
    }

    public final void set(Line line, String value){
        consumer.accept(line, transform.apply(value));
    }

}
