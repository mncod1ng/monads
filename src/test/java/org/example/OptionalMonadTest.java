package org.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class OptionalMonadTest {

    @Test
    void optional_of_is_a_left_identity() {
        Optional<String> m_a = Optional.of("a");

        Function<String, Optional<String>> unit = Optional::of;

        Optional<String> result = m_a.flatMap(unit);

        assertThat(result, is(m_a));
    }

    @Test
    void optional_of_is_a_right_identity() {
        String a = "a";

        Function<String, Optional<? extends String>> unit = Optional::of;

        Function<String, Optional<Integer>> f = (String string) -> Optional.of(string.length());


        Optional<Integer> f_a = f.apply(a);

        Optional<Integer> result = unit.apply(a).flatMap(f);


        assertThat(result, is(f_a));
    }

    @Disabled
    @Test
    void optional_of_is_a_right_identity_empty_fails() {

        Function<String, Optional<? extends String>> unit = Optional::of;

        Function<String, Optional<Integer>> f = (String string) -> Optional.of(string.length());


        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.apply(null).flatMap(f);


        assertThat(result, is(f_a));
    }

    @Test
    void flat_map_is_associative() {
        Optional<String> m_a = Optional.of("a");

        Function<String, Optional<Integer>> f = (String string) -> Optional.of(string.length());
        Function<Integer, Optional<Boolean>> g = (Integer integer) -> Optional.of(integer % 2 == 0);

        Function<Optional<String>, Optional<Integer>> bind_f = (Optional<String> optStr) -> optStr.flatMap(f);
        Function<Optional<Integer>, Optional<Boolean>> bind_g = (Optional<Integer> optInt) -> optInt.flatMap(g);

        Function<Optional<String>, Optional<Boolean>> bind_g_o_bind_f = bind_g.compose(bind_f);

        Optional<Boolean> result_1 = bind_g_o_bind_f.apply(m_a);


        Function<String, Optional<Boolean>> bind_g_o_f = bind_g.compose(f);


        Optional<Boolean> result_2 = m_a.flatMap(bind_g_o_f);

        assertThat(result_1, is(result_2));
    }

    @Test
    void optional_of_is_a_left_identity_empty() {
        Optional<String> m_a = Optional.empty();

        Function<String, Optional<? extends String>> unit = Optional::of;

        Optional<String> result = m_a.flatMap(unit);

        assertThat(result, is(m_a));
    }

    @Disabled
    @Test
    void optional_of_is_a_right_identity_empty_fixed() {

        Function<String, Optional<? extends String>> unit = Optional::ofNullable;

        Function<String, Optional<Integer>> f = OptionalFunction.of(String::length);


        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.apply(null).flatMap(f);


        assertThat(result, is(f_a));
    }
}