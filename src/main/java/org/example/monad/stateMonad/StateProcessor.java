package org.example.monad.stateMonad;

import java.util.function.Function;

public class StateProcessor<S, A> {

    private final Function<S, Pair<A, S>> runState;

    public Pair<A, S> runState(S s){
        return runState.apply(s);
    }

    public StateProcessor(Function<S, Pair<A, S>> runState) {
        this.runState = runState;
    }

    public static <S, A> StateProcessor<S, A> unit(A x) {
        return new StateProcessor<>((S s) -> new Pair<>(x, s));
    }

    public <B> StateProcessor<S, B> bind(Function<A, StateProcessor<S, B>> f) {
        return new StateProcessor<>((S s1) -> {

            Pair<A, S> pair_v1_s2 = runState(s1);

            A v1 = pair_v1_s2.getFirst();

            StateProcessor<S, B> pB= f.apply(v1);

            S s2 = pair_v1_s2.getSecond();

            return pB.runState(s2);
        });
    }

    public Function<S, Pair<S, S>> get() {
        return (S s) -> {
            S copy = runState(s).getSecond();
            return new Pair<>(copy, copy);
        };
    }
}
