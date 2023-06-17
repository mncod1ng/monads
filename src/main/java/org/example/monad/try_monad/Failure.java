package org.example.monad.try_monad;

import org.example.monad.try_monad.definitions.ThrowableSupplier;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class Failure<T> extends Try<T> {
    private final Throwable e;

    Failure(Throwable e) {
        this.e = e;
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
        return Try.to(f);
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> f) {
        Objects.requireNonNull(f);
        return Try.failure(e);
    }

    @Override
    public <U> Try<U> map(Function<? super T, U> f) {
        return new Failure<>(e);
    }

    @Override
    public Try<T> failMap(Function<Throwable, Throwable> errorFunction) {
        return new Failure<>(errorFunction.apply(e));
    }

    @Override
    public T get() throws Throwable {
        throw e;
    }

    @Override
    public boolean failed() {
        return true;
    }

    @Override
    public T orElse(T value) {
        return value;
    }

    @Override
    public T doCatch(Function<Throwable, T> catchFail) {
        return catchFail.apply(e);
    }


}
