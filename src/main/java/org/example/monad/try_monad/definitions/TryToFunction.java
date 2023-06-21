package org.example.monad.try_monad.definitions;

import org.example.monad.try_monad.Try;

import java.util.function.Function;

public interface TryToFunction<A, B> extends Function<Try<A>, Try<B>> {

    default Try<B> tryApply(A a){
        return this.apply(Try.suceeds(a));
    }

    default <C> TryToFunction<A, C> thenTryTo(ThrowableFunction<B, C> after){
        TryToFunction<B, C> tryToFunction = Try.to(after);
        return (Try<A> tryA) -> {
            Try<B> tryB = this.apply(tryA);
            return tryToFunction.apply(tryB);
        };
    }
}
