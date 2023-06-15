package org.example.kleisli_category;

import java.util.Optional;
import java.util.function.Function;

/**
 * An arrow in the kleisli category for the optional monad endofunctor
 * is a function from a type to optional type
 */
public interface KleisliCategoryArrow<A, B> extends Function<A, Optional<B>> {

    static <T> KleisliCategoryArrow<T,T> unit() {
        return Optional::of;
    }

    static <T,U> KleisliCategoryArrow<T, U> arrow(Function<T, U> function){
        return (T t) -> Optional.ofNullable(t).map(function);
    }

    default  <C> KleisliCategoryArrow<A, C> kleisliOperator(KleisliCategoryArrow<B, C> arrow){
        return (A a) -> apply(a).flatMap(arrow);
        // return (A a) -> Optional.ofNullable(a).flatMap(it -> apply(it).flatMap(arrow));

    };
    static <T> KleisliCategoryArrow<T, T> unit_empty_fixed() {
        return Optional::ofNullable;
    }

}
