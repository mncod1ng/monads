package org.example.monad.optional;

import org.example.kleisli.KleisliCategoryOptArrow;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class OptionalTest {

    @Test
    void optional_of_is_a_left_identity() {
        KleisliCategoryOptArrow<String, Integer> f = (String string) -> Optional.of(string.length());

        Optional<Integer> f_a = f.apply("a");

        KleisliCategoryOptArrow<Integer, Integer> unit = KleisliCategoryOptArrow.unit();

        Optional<Integer> result = f.kleisliOperator(unit).apply("a");

        assertThat(result, is(f_a));
    }

    @Test
    void optional_of_is_a_right_identity() {
        String a = "a";

        KleisliCategoryOptArrow<String, String> unit = Optional::of;

        KleisliCategoryOptArrow<String, Integer> f = (String string) -> Optional.of(string.length());


        Optional<Integer> f_a = f.apply(a);

        Optional<Integer> result = unit.kleisliOperator(f).apply(a);


        assertThat(result, is(f_a));
    }


    @Disabled
    @Test
    void optional_of_is_a_right_identity_empty_fails() {

        KleisliCategoryOptArrow<String, String> unit = KleisliCategoryOptArrow.unit();

        KleisliCategoryOptArrow<String, Integer> f = (String string) -> Optional.of(string.length());


        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);


        assertThat(result, is(f_a));
    }

    @Test
    void kleisli_operator_is_associative() {
        KleisliCategoryOptArrow<String, Integer> f = KleisliCategoryOptArrow.arrow(String::length);
        KleisliCategoryOptArrow<Integer, Boolean> g = KleisliCategoryOptArrow.arrow((Integer integer) -> integer % 2 == 0);
        KleisliCategoryOptArrow<Boolean, String> h = KleisliCategoryOptArrow.arrow((Boolean bool) -> Boolean.toString(bool));

        KleisliCategoryOptArrow<String, Boolean> f_fishy_g = f.kleisliOperator(g);
        KleisliCategoryOptArrow<Integer, String> g_fishy_h = g.kleisliOperator(h);

        Optional<String> result_1 = f_fishy_g.kleisliOperator(h).apply("a");

        Optional<String> result_2 = f.kleisliOperator(g_fishy_h).apply("a");


        assertThat(result_1, is(result_2));
    }

    @Disabled
    @Test
    void optional_of_is_a_left_identity_empty_fails() {

        KleisliCategoryOptArrow<String, String> unit = KleisliCategoryOptArrow.unit();

        KleisliCategoryOptArrow<String, Integer> f = KleisliCategoryOptArrow.arrow(String::length);

        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);

        assertThat(result, is(f_a));
    }

    @Test
    void optional_of_is_a_left_identity_empty_fixed() {

        KleisliCategoryOptArrow<String, String> unit = KleisliCategoryOptArrow.unit_empty_fixed();

        KleisliCategoryOptArrow<String, Integer> f = KleisliCategoryOptArrow.arrow(String::length);

        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = unit.kleisliOperator(f).apply(null);

        assertThat(result, is(f_a));
    }

    @Test
    void optional_of_is_a_right_identity_empty_fixed() {

        KleisliCategoryOptArrow<Integer, Integer> unit = KleisliCategoryOptArrow.unit();

        KleisliCategoryOptArrow<String, Integer> f = KleisliCategoryOptArrow.arrow(String::length);


        Optional<Integer> f_a = f.apply(null);

        Optional<Integer> result = f.kleisliOperator(unit).apply(null);


        assertThat(result, is(f_a));
    }
}