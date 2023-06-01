package org.example.monad.tryMonad;


import java.util.Objects;
import java.util.function.Function;

public abstract class Try<T> {

    public interface ThrowableSupplier<S> {
        //Lesson: If you comment me out, you get compile errors. :-)
        S get() throws Throwable;

    }

    public static <U> Try<U> to(ThrowableSupplier<U> f) {
        Objects.requireNonNull(f);
        try {
            return Try.successful(f.get());
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    public static <U> Success<U> successful(U u) {
        return new Success<>(u);
    }
    public static <U> Failure<U> failure(Throwable e) {
        return new Failure<>(e);
    }

    public abstract <U> Try<U> flatMap(Function<? super T, Try<U>> f);

    public abstract T get() throws Throwable;

    public abstract T orElse(T value);
}
