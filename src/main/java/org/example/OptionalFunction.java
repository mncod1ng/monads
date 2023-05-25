package org.example;

import java.util.Optional;
import java.util.function.Function;

public class OptionalFunction {

    public static <T,U> Function<T, Optional<U>> of(Function<T, U> function){
        return (T t) -> Optional.ofNullable(t).flatMap(it -> Optional.ofNullable(function.apply(it)));
    }
}
