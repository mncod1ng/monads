package org.example.monad.pipe_monad;

import java.util.function.Function;
import java.util.stream.Stream;

public class Pipe<A,B> {

    private final Function<A, B> ab;

    private Pipe(Function<A,B> ab) {
        this.ab = ab;
    }
    public static <T, R> Pipe<T,R> of(Function<T, R> func){
        return new Pipe<>(func);
    }

    public <C,D> Pipe<C,D> transport(Function<Function<A, B>, Function<C, D>> transport){
        return new Pipe<>(transport.apply(this.ab));
    }
    @SafeVarargs
    public final Pipe<A, B> lchain(Function<A, A>... funcA){
        return new Pipe<>(Stream.of(funcA).reduce(Function.identity(), Function::andThen).andThen(this.ab));
    }

    @SafeVarargs
    public final Pipe<A,B> rchain(Function<B, B>... funcB){
        return new Pipe<>(this.ab.andThen(Stream.of(funcB).reduce(Function.identity(), Function::andThen)));
    }

    public <C> Pipe<A, C> map(Function<B, C> bc){
        return new Pipe<>(this.ab.andThen(bc));
    }



    public B get(A a){
        return ab.apply(a);
    }
}
