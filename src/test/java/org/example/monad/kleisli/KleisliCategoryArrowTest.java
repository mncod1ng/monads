package org.example.monad.kleisli;

import org.example.kleisli_category.KleisliCategoryArrow;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;


class KleisliCategoryArrowTest {

    @Test
    void optional_of_is_a_left_identity() {
        KleisliCategoryArrow<String, Integer> f = (String string) -> Optional.of(string.length());

        Optional<Integer> f_a = f.apply("a");

        KleisliCategoryArrow<Integer, Integer> unit = KleisliCategoryArrow.unit();

        Optional<Integer> result = f.kleisliOperator(unit).apply("a");

        assertThat(result, Matchers.is(f_a));
    }

    @Test
    void optional_of_is_a_right_identity() {
        String a = "a";

        KleisliCategoryArrow<String, String> unit = Optional::of;

        KleisliCategoryArrow<String, Integer> f = (String string) -> Optional.of(string.length());


        Optional<Integer> f_a = f.apply(a);

        Optional<Integer> result = unit.kleisliOperator(f).apply(a);


        assertThat(result, Matchers.is(f_a));
    }


    @Disabled
    @Test
    void optional_of_is_a_right_identity_empty_fails() {

        KleisliCategoryArrow<String, String> unit = KleisliCategoryArrow.unit();

        KleisliCategoryArrow<String, Integer> f = (String string) -> Optional.of(string.length());


        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);


        assertThat(result, Matchers.is(f_a));
    }

    @Test
    void kleisli_operator_is_associative() {
        KleisliCategoryArrow<String, Integer> f = KleisliCategoryArrow.arrow(String::length);
        KleisliCategoryArrow<Integer, Boolean> g = KleisliCategoryArrow.arrow((Integer integer) -> integer % 2 == 0);
        KleisliCategoryArrow<Boolean, String> h = KleisliCategoryArrow.arrow((Boolean bool) -> Boolean.toString(bool));

        KleisliCategoryArrow<String, Boolean> f_fishy_g = f.kleisliOperator(g);
        KleisliCategoryArrow<Integer, String> g_fishy_h = g.kleisliOperator(h);

        Optional<String> result_1 = f_fishy_g.kleisliOperator(h).apply("a");

        Optional<String> result_2 = f.kleisliOperator(g_fishy_h).apply("a");


        assertThat(result_1, Matchers.is(result_2));
    }

    @Disabled
    @Test
    void optional_of_is_a_left_identity_empty_fails() {

        KleisliCategoryArrow<String, String> unit = KleisliCategoryArrow.unit();

        KleisliCategoryArrow<String, Integer> f = KleisliCategoryArrow.arrow(String::length);

        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);

        assertThat(result, Matchers.is(f_a));
    }

    @Test
    void optional_of_is_a_left_identity_empty_fixed() {

        KleisliCategoryArrow<String, String> unit = KleisliCategoryArrow.unit_empty_fixed();

        KleisliCategoryArrow<String, Integer> f = KleisliCategoryArrow.arrow(String::length);

        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);

        assertThat(result, Matchers.is(f_a));
    }

    @Test
    void optional_of_is_a_right_identity_empty_fixed() {

        KleisliCategoryArrow<Integer, Integer> unit = KleisliCategoryArrow.unit();

        KleisliCategoryArrow<String, Integer> f = KleisliCategoryArrow.arrow(String::length);


        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = f.kleisliOperator(unit).apply(null);


        assertThat(result, Matchers.is(f_a));
    }
}