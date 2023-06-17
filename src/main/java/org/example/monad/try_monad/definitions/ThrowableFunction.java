package org.example.monad.try_monad.definitions;

@FunctionalInterface
public interface ThrowableFunction<A, B> {
    B apply(A a) throws Throwable;
}
