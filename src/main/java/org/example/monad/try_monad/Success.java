package org.example.monad.try_monad;

import org.example.monad.try_monad.definitions.ThrowableFunction;
import org.example.monad.try_monad.definitions.ThrowableSupplier;

import java.util.Objects;
import java.util.function.Function;

public final class Success<T> extends Try<T> {
    private final T value;

    Success(T value) {
        this.value = value;
    }

    @Override
    public Try<T> unless(ThrowableSupplier<T> f) {
        Try<T> other = Try.to(f);
        if (other.failed()) {
            return this;
        }
        return other;
    }

    @Override
    public Try<T> orElseTry(ThrowableSupplier<T> f) {
        return this;
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> f) {
        Objects.requireNonNull(f);
        return f.apply(value);
    }

    @Override
    public <U> Try<U> map(Function<? super T, U> f) {
        Objects.requireNonNull(f);
        return Try.to(() -> f.apply(value));
    }

    @Override
    public Try<T> failMap(Function<Throwable, Throwable> errorFunction) {
        return this;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public boolean failed() {
        return false;
    }

    @Override
    public T orElse(T value) {
        return this.value;
    }

    @Override
    public T doCatch(Function<Throwable, T> catchFail) {
        return value;
    }

    @Override
    public <U> Try<U> thenTry(ThrowableFunction<T, U> f) {
        try {
            return new Success<>(f.apply(value));
        } catch (Throwable e) {
            return Try.fails(e);
        }
    }

}
