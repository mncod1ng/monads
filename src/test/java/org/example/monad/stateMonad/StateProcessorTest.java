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

        StateProcessor<State, String> p_B = p_A.bind(unit);

        assertThatBothStateProcessorsAreEqual(p_A, p_B);
    }


    @Test
    void compute_example_one_step() {
        String v_0 = "-o----";
        StateProcessor<State, String> start = StateProcessor.unit(v_0);

        Function<String, StateProcessor<State, String>> f = (v) -> new StateProcessor<>(RunInCyclesChart.get(v));
        StateProcessor<State, String> step_1 = start.bind(f);

        Pair<String, State> step_2 = step_1.runState(GO_RIGHT);

        assertThat(step_2.getSecond(), Matchers.is(GO_RIGHT));
        assertThat(step_2.getFirst(), Matchers.is("--o---"));
    }

    @Test
    void compute_example_two_steps() {
        String v_0 = "-o----";
        StateProcessor<State, String> start = StateProcessor.unit(v_0);

        Function<String, StateProcessor<State, String>> nextStep = (v) -> new StateProcessor<>(RunInCyclesChart.get(v));
        StateProcessor<State, String> process_step_3 = start
                .bind(nextStep)
                .bind(nextStep);

        Pair<String, State> step_3 = process_step_3.runState(GO_RIGHT);

        assertThat(step_3.getSecond(), Matchers.is(GO_RIGHT));
        assertThat(step_3.getFirst(), Matchers.is("---o--"));
    }

    @Test
    void compute_example_six_steps() {
        String v_0 = "-o----";
        StateProcessor<State, String> initialState = StateProcessor.unit(v_0);

        Function<String, StateProcessor<State, String>> computeRunInCyclesStep = (v) -> new StateProcessor<>(RunInCyclesChart.get(v));

        StateProcessor<State, String> processSixSteps = initialState
                .bind(computeRunInCyclesStep)
                .bind(computeRunInCyclesStep)
                .bind(computeRunInCyclesStep)
                .bind(computeRunInCyclesStep)
                .bind(computeRunInCyclesStep)
                .bind(computeRunInCyclesStep);

        Pair<String, State> step_6 = processSixSteps.runState(GO_RIGHT);

        assertThat(step_6.getSecond(), Matchers.is(GO_LEFT));
        assertThat(step_6.getFirst(), Matchers.is("----o-"));
    }

    private static void assertThatBothStateProcessorsAreEqual(StateProcessor<State, String> m_a, StateProcessor<State, String> result) {
        Arrays.stream(State.values()).forEach(state -> {
            assertThatBothStateProcessorsProcessStateWithSameResult(m_a, result, state);
        });
    }

    private static void assertThatBothStateProcessorsProcessStateWithSameResult(StateProcessor<State, String> m_a, StateProcessor<State, String> result, State state) {
        assertThat(result.runState(state).getFirst(), Matchers.is(m_a.runState(state).getFirst()));
        assertThat(result.runState(state).getSecond(), Matchers.is(m_a.runState(state).getSecond()));
    }
}