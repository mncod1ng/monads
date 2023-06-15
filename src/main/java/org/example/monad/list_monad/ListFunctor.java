package org.example.monad.list_monad;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The list monad is an (endo-)functor for the category of types and functions
 * It maps the objects and the functions
 * The image of the functor with regard to types is given by List<T>
 * Computes partial / null returing functions.
 * @param <A>
 * @param <B>
 */
public class ListFunctor<A, B> implements Function<Function<A, B>, Function<List<A>, List<B>>> {


    @Override
    public Function<List<A>, List<B>> apply(Function<A, B> function) {
        return (List<A> listA) -> listA.stream().map(function).collect(Collectors.toList());
    }

    //fmap for types
    public static <T> List<T> fmap(T t){
        return List.of(t);
    }

    //fmap for functions
    public static <T,R> Function<List<T>, List<R>> fmap(Function<T, R> function){
        ListFunctor<T, R> listFunctor = new ListFunctor<>();
        return listFunctor.apply(function);
    }

}
