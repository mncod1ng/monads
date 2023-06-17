package org.example.monad_definition;

import java.util.Optional;
import java.util.function.Function;

/**
 * The optional monad is an (endo-)functor for the category of types and functions
 * with certain additional assumptions
 * It maps the objects and the functions
 * The image of the functor with regard to types is given by Optional<T>
 * Computes partial / null returing functions.
 * @param <A>
 * @param <B>
 */
public class OptionalFunctor<A, B> implements Function<Function<A, B>, Function<Optional<A>, Optional<B>>> {

    //fmap for functions
    @Override
    public Function<Optional<A>, Optional<B>> apply(Function<A, B> function) {
        return (Optional<A> optA) -> optA.map(function);
    }

    //fmap for types
    public static <T> Optional<T> fmap(T t){
        return Optional.ofNullable(t);
    }

    public static <T,R> Function<Optional<T>, Optional<R>> fmap(Function<T, R> function){
        OptionalFunctor<T, R> optionalFunctor = new OptionalFunctor<>();
        return optionalFunctor.apply(function);
    }

}
