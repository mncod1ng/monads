package org.example.monad.try_monad;

import java.util.Objects;
import java.util.function.Function;

public class Success<T> extends Try<T> {
    private final T value;

    Success(T value) {
        this.value = value;
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> f) {
        Objects.requireNonNull(f);
        return f.apply(value);
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T orElse(T value) {
        return this.value;
    }
}
