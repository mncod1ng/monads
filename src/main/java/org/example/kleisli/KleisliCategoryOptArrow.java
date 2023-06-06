package org.example.kleisli;

import java.util.Optional;
import java.util.function.Function;


public interface KleisliCategoryOptArrow<A, B> extends Function<A, Optional<B>> {

    static <T> KleisliCategoryOptArrow<T, T> unit() {
        return Optional::of;
    }

    static <T> KleisliCategoryOptArrow<T, T> unit_empty_fixed() {
        return Optional::ofNullable;
    }

    static <T,U> KleisliCategoryOptArrow<T, U> arrow(Function<T, U> function){
        return (T t) -> Optional.ofNullable(t).map(function);
    }

    default  <C> KleisliCategoryOptArrow<A, C> kleisliOperator(KleisliCategoryOptArrow<B, C> arrow){
        return (A a) -> apply(a).flatMap(arrow);
        // return (A a) -> Optional.ofNullable(a).flatMap(it -> apply(it).flatMap(arrow));
    };
}
