package org.example.monad.stateMonad;

import java.util.function.Function;

import static org.example.monad.stateMonad.State.GO_LEFT;
import static org.example.monad.stateMonad.State.GO_RIGHT;

public class RunInCyclesChart {

    public static Function<State, Pair<String, State>> get(String v) {
        return state -> {
            switch (state) {
                case GO_LEFT -> {
                    int pos = v.indexOf("o");
                    if (pos > 0) {
                        String moved_left = v.substring(0, pos - 1) + "o-" + v.substring(pos + 1);
                        return new Pair<>(moved_left, GO_LEFT);
                    } else {
                        return new Pair<>(v, GO_RIGHT);
                    }
                }
                case GO_RIGHT -> {
                    int pos = v.indexOf("o");
                    if (pos < v.length() - 1) {
                        String moved_right = v.substring(0, pos) + "-o" + v.substring(pos + 2);
                        return new Pair<>(moved_right, GO_RIGHT);
                    } else {
                        return new Pair<>(v, GO_LEFT);
                    }
                }
            }
            return null;
        };
    }
}
