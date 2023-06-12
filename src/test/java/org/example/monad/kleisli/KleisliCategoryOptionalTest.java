package org.example.monad.kleisli;

import org.example.kleisli_category.KleisliCategoryOptional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class KleisliCategoryOptionalTest {

    @Test
    void optional_of_is_a_left_identity() {
        KleisliCategoryOptional<String, Integer> f = (String string) -> Optional.of(string.length());

        Optional<Integer> f_a = f.apply("a");

        KleisliCategoryOptional<Integer, Integer> unit = KleisliCategoryOptional.unit();

        Optional<Integer> result = f.kleisliOperator(unit).apply("a");

        assertThat(result, is(f_a));
    }

    @Test
    void optional_of_is_a_right_identity() {
        String a = "a";

        KleisliCategoryOptional<String, String> unit = Optional::of;

        KleisliCategoryOptional<String, Integer> f = (String string) -> Optional.of(string.length());


        Optional<Integer> f_a = f.apply(a);

        Optional<Integer> result = unit.kleisliOperator(f).apply(a);


        assertThat(result, is(f_a));
    }


    @Disabled
    @Test
    void optional_of_is_a_right_identity_empty_fails() {

        KleisliCategoryOptional<String, String> unit = KleisliCategoryOptional.unit();

        KleisliCategoryOptional<String, Integer> f = (String string) -> Optional.of(string.length());


        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);


        assertThat(result, is(f_a));
    }

    @Test
    void kleisli_operator_is_associative() {
        KleisliCategoryOptional<String, Integer> f = KleisliCategoryOptional.arrow(String::length);
        KleisliCategoryOptional<Integer, Boolean> g = KleisliCategoryOptional.arrow((Integer integer) -> integer % 2 == 0);
        KleisliCategoryOptional<Boolean, String> h = KleisliCategoryOptional.arrow((Boolean bool) -> Boolean.toString(bool));

        KleisliCategoryOptional<String, Boolean> f_fishy_g = f.kleisliOperator(g);
        KleisliCategoryOptional<Integer, String> g_fishy_h = g.kleisliOperator(h);

        Optional<String> result_1 = f_fishy_g.kleisliOperator(h).apply("a");

        Optional<String> result_2 = f.kleisliOperator(g_fishy_h).apply("a");


        assertThat(result_1, is(result_2));
    }

    @Disabled
    @Test
    void optional_of_is_a_left_identity_empty_fails() {

        KleisliCategoryOptional<String, String> unit = KleisliCategoryOptional.unit();

        KleisliCategoryOptional<String, Integer> f = KleisliCategoryOptional.arrow(String::length);

        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);

        assertThat(result, is(f_a));
    }

    @Test
    void optional_of_is_a_left_identity_empty_fixed() {

        KleisliCategoryOptional<String, String> unit = KleisliCategoryOptional.unit_empty_fixed();

        KleisliCategoryOptional<String, Integer> f = KleisliCategoryOptional.arrow(String::length);

        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);

        assertThat(result, is(f_a));
    }

    @Test
    void optional_of_is_a_right_identity_empty_fixed() {

        KleisliCategoryOptional<Integer, Integer> unit = KleisliCategoryOptional.unit();

        KleisliCategoryOptional<String, Integer> f = KleisliCategoryOptional.arrow(String::length);


        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = f.kleisliOperator(unit).apply(null);


        assertThat(result, is(f_a));
    }
}