package org.example.monad.try_monad;


import java.util.Objects;
import java.util.function.Function;

public abstract class Try<T> {

    public interface ThrowableSupplier<S> {
        //Lesson: If you comment me out, you get compile errors. :-)
        S get() throws Throwable;

    }

    @FunctionalInterface
    public interface ThrowableFunction<A, B> {
        B apply(A a) throws Throwable;
    }

    public static <U> Try<U> to(ThrowableSupplier<U> f) {
        Objects.requireNonNull(f);
        try {
            return Try.successful(f.get());
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    public static <A, B> Function<Try<A>, Try<B>> to(ThrowableFunction<A, B> f) {
        Objects.requireNonNull(f);
        return (Try<A> tryA) -> {
            try {
                A a = tryA.justDoIt();
                return Try.successful(f.apply(a));
            } catch (Throwable e) {
                return Try.failure(e);
            }
        };
    }

    public static <U> Success<U> successful(U u) {
        return new Success<>(u);
    }

    public static <U> Failure<U> failure(Throwable e) {
        return new Failure<>(e);
    }

    public abstract Try<T> unless(ThrowableSupplier<T> f);

    public abstract Try<T> orElseTry(ThrowableSupplier<T> f);

    public abstract <U> Try<U> flatMap(Function<? super T, Try<U>> f);

    public abstract <U> Try<U> map(Function<? super T, U> f);

    public abstract Try<T> failMap(Function<Throwable, Throwable> errorFunction);

    public abstract T justDoIt() throws Throwable;

    public abstract boolean failed();

    public abstract T orElse(T value);
}
