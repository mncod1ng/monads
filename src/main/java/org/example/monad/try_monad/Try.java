package org.example.monad.try_monad;


import org.example.monad.try_monad.definitions.ThrowableFunction;
import org.example.monad.try_monad.definitions.ThrowableSupplier;
import org.example.monad.try_monad.definitions.TryToFunction;

import java.util.Objects;
import java.util.function.Function;

public abstract sealed class Try<T> permits Success, Failure {

    public static <U> Try<U> to(ThrowableSupplier<U> f) {
        Objects.requireNonNull(f);
        try {
            return Try.suceeds(f.get());
        } catch (Throwable e) {
            return Try.fails(e);
        }
    }

    public static <A, B> TryToFunction<A, B> to(ThrowableFunction<A, B> f) {
        Objects.requireNonNull(f);
        return (Try<A> tryA) -> {
            try {
                A a = tryA.get();
                return Try.suceeds(f.apply(a));
            } catch (Throwable e) {
                return Try.fails(e);
            }
        };
    }

    public static <U> Try<U> of(U value){
        return Try.suceeds(value);
    }

    public static <U> Success<U> suceeds(U neverFails) {
        return new Success<>(neverFails);
    }

    public static <U> Failure<U> fails(Throwable e) {
        return new Failure<>(e);
    }

    public abstract Try<T> unless(ThrowableSupplier<T> f);

    public abstract Try<T> orElseTry(ThrowableSupplier<T> f);

    public abstract <U> Try<U> flatMap(Function<? super T, Try<U>> f);

    public abstract <U> Try<U> map(Function<? super T, U> f);

    public abstract Try<T> failMap(Function<Throwable, Throwable> errorFunction);

    public abstract T get() throws Throwable;

    public abstract boolean failed();

    public abstract T orElse(T value);

    public abstract T doCatch(Function<Throwable, T> catchFail);

    public abstract  <U> Try<U> thenTry(ThrowableFunction<T, U> f);
}
