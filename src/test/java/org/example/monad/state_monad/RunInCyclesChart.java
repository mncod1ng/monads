package org.example.monad.state_monad;

import java.util.function.Function;

import static org.example.monad.state_monad.State.GO_LEFT;
import static org.example.monad.state_monad.State.GO_RIGHT;

public class RunInCyclesChart {

    public static Function<State, Pair<String, State>> get(String route) {
        return state -> {
            switch (state) {
                case GO_LEFT -> {
                    return go_left_as_far_as_you_can(route);
                }
                case GO_RIGHT -> {
                    return go_right_as_far_as_you_can(route);
                }
            }
            return turn_left(route);
        };
    }

    private static Pair<String, State> go_right_as_far_as_you_can(String route) {
        int your_current_position = where_you_are(route);
        if (you_are_not_at_the_right_border(route, your_current_position)) {
            return go_on_right(route, your_current_position);
        } else {
            return turn_left(route);
        }
    }

    private static boolean you_are_not_at_the_right_border(String v, int the_current_position) {
        return the_current_position < v.length() - 1;
    }

    private static Pair<String, State> go_on_right(String v, int the_current_position) {
        String moved_right = v.substring(0, the_current_position) + "-o" + v.substring(the_current_position + 2);
        return new Pair<>(moved_right, GO_RIGHT);
    }

    private static Pair<String, State> go_left_as_far_as_you_can(String v) {
        int the_current_position = where_you_are(v);
        if (you_are_not_at_left_border(the_current_position)) {
            return go_on_left(v, the_current_position);
        } else {
            return turn_right(v);
        }
    }

    private static Pair<String, State> go_on_left(String v, int the_current_position) {
        String moved_left = v.substring(0, the_current_position - 1) + "o-" + v.substring(the_current_position + 1);
        return turn_left(moved_left);
    }

    private static boolean you_are_not_at_left_border(int the_current_position) {
        return the_current_position > 0;
    }

    private static int where_you_are(String v) {
        return v.indexOf("o");
    }

    private static Pair<String, State> turn_left(String v) {
        return new Pair<>(v, GO_LEFT);
    }

    private static Pair<String, State> turn_right(String v) {
        return new Pair<>(v, GO_RIGHT);
    }
}
