package org.example.monad.try_monad;

import java.util.Objects;
import java.util.function.Function;

public class Failure<T>  extends Try<T> {
    private final Throwable e;

    Failure(Throwable e) {
        this.e = e;
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> f) {
        Objects.requireNonNull(f);
        return Try.failure(e);
    }

    @Override
    public T get() throws Throwable {
        throw e;
    }

    @Override
    public T orElse(T value) {
        return value;
    }
}
