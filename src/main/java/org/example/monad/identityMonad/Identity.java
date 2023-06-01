package org.example.monad.identityMonad;

import java.util.Objects;
import java.util.function.Function;

public class Identity<T> {
    private final T value;

    private Identity(T value) {
        this.value = value;
    }

    public static <S> Identity<S> unit(S value){
        return new Identity<>(value);
    }

    public <S> Identity<S> bind(Function<T, Identity<S>> f){
        return f.apply(value);
    }

    public <S> Identity<S> map(Function<T, S> f){
        return Identity.unit(f.apply(value));
    }

    public T get() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity<?> identity)) return false;
        return Objects.equals(value, identity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
