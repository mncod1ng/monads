package org.example.monad.stateMonad;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Function;

import static org.example.monad.stateMonad.State.*;
import static org.hamcrest.MatcherAssert.assertThat;

class StateProcessorTest {


    @Test
    void should_get_value_of_initial_state() {
        StateProcessor<State, String> initialState = StateProcessor.unit("-o----");


        Function<State, Pair<State, State>> runCurrentState = initialState.get();

        State state = GO_LEFT;

        Pair<State, State> currentState = runCurrentState.apply(state);

        assertThat(currentState.getFirst(), Matchers.is(state));
    }

    @Test
    void state_processor_unit_is_a_left_identity() {
        StateProcessor<State, String> p_A = StateProcessor.unit("o----");

        Function<String, StateProcessor<State, String>> unit = StateProcessor::unit;

        StateProcessor<State, String> p_B = p_A.process(unit);

        assertThatBothStateProcessorsAreEqual(p_A, p_B);
    }


    @Test
    void compute_example_one_step() {
        StateProcessor<State, String> start = StateProcessor.unit("-o----");

        Function<String, StateProcessor<State, String>> step = (v) -> new StateProcessor<>(RunInCyclesChart.get(v));

        StateProcessor<State, String> process_one_step = start.process(step);

        Pair<String, State> state_after_one_step = process_one_step.runState(GO_RIGHT);

        assertThat(state_after_one_step.getSecond(), Matchers.is(GO_RIGHT));
        assertThat(state_after_one_step.getFirst(), Matchers.is("--o---"));
    }

    @Test
    void compute_example_two_steps() {
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
    void compute_example_six_steps() {
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

    private static void assertThatBothStateProcessorsAreEqual(StateProcessor<State, String> processor_1, StateProcessor<State, String> processor_2) {
        State[] allPossibleStates = values();
        Arrays.stream(allPossibleStates).forEach(state -> {
            assertThatBothStateProcessorsProcessStateWithSameResult(processor_1, processor_2, state);
        });
    }

    private static void assertThatBothStateProcessorsProcessStateWithSameResult(StateProcessor<State, String> processor_1, StateProcessor<State, String> processor_2, State state) {
        Pair<String, State> result_1 = processor_1.runState(state);
        Pair<String, State> result_2 = processor_2.runState(state);
        assertThat(result_1.getFirst(), Matchers.is(result_2.getFirst()));
        assertThat(result_1.getSecond(), Matchers.is(result_2.getSecond()));
    }
}