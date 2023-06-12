package org.example.kleisli_category;

import java.util.Optional;
import java.util.function.Function;


public interface KleisliCategoryOptional<A, B> extends Function<A, Optional<B>> {

    static <T> KleisliCategoryOptional<T,T> unit() {
        return Optional::of;
    }

    static <T,U> KleisliCategoryOptional<T, U> arrow(Function<T, U> function){
        return (T t) -> Optional.ofNullable(t).map(function);
    }

    default  <C> KleisliCategoryOptional<A, C> kleisliOperator(KleisliCategoryOptional<B, C> arrow){
        return (A a) -> apply(a).flatMap(arrow);
        // return (A a) -> Optional.ofNullable(a).flatMap(it -> apply(it).flatMap(arrow));

    };
    static <T> KleisliCategoryOptional<T, T> unit_empty_fixed() {
        return Optional::ofNullable;
    }

}
