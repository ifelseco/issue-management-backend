package com.ifelseco.issueapp.mapping;

import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface  // it is only for validation. if there will be created more than one abstract method, then compiler will give error.
public interface Converter<T,R> {

    R convert(T t);

    default List<R> convertAll(List<T> list) {
        return list.stream()
                .map(x -> convert(x))
                .collect(Collectors.toList());
    }
}
