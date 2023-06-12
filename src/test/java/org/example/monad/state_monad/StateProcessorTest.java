package org.example.monad.state_monad;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.example.monad.state_monad.State.*;
import static org.hamcrest.MatcherAssert.assertThat;

class StateProcessorTest {


    @Test
    void compute_one_step() {
        StateProcessor<State, String> start = StateProcessor.unit("-o----");

        Function<String, StateProcessor<State, String>> step = (v) -> new StateProcessor<>(RunInCyclesChart.get(v));

        StateProcessor<State, String> process_one_step = start.process(step);

        Pair<String, State> state_after_one_step = process_one_step.runState(GO_RIGHT);

        assertThat(state_after_one_step.getSecond(), Matchers.is(GO_RIGHT));
        assertThat(state_after_one_step.getFirst(), Matchers.is("--o---"));
    }

    @Test
    void compute_two_steps() {
        StateProcessor<State, String> start = StateProcessor.unit("-o----");

        Function<String, StateProcessor<State, String>> step = (v) -> new StateProcessor<>(RunInCyclesChart.get(v));

        StateProcessor<State, String> process_two_steps = start
                .process(step)
                .process(step);

        Pair<String, State> state_after_two_steps = process_two_steps.runState(GO_RIGHT);

        assertThat(state_after_two_steps.getSecond(), Matchers.is(GO_RIGHT));
        assertThat(state_after_two_steps.getFirst(), Matchers.is("---o--"));
    }

    @Test
    void compute_six_steps() {
        StateProcessor<State, String> start = StateProcessor.unit("-o----");

        Function<String, StateProcessor<State, String>> step = (v) -> new StateProcessor<>(RunInCyclesChart.get(v));

        StateProcessor<State, String> process_six_steps = start
                .process(step)
                .process(step)
                .process(step)
                .process(step)
                .process(step)
                .process(step);

        Pair<String, State> state_after_six_steps = process_six_steps.runState(GO_RIGHT);

        assertThat(state_after_six_steps.getSecond(), Matchers.is(GO_LEFT));
        assertThat(state_after_six_steps.getFirst(), Matchers.is("----o-"));
    }
}