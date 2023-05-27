package org.example.monad.identityMonad;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class IdentityTest {

    public static final LocalDate TEST_DATE = LocalDate.of(1970, 1, 1);

    @Test
    void unit_is_a_left_identity() {
        Identity<LocalDate> m_a = Identity.unit(TEST_DATE);

        Function<LocalDate, Identity<LocalDate>> unit = Identity::unit;

        Identity<LocalDate> result = m_a.bind(unit);

        assertThat(result, is(m_a));
    }

    @Test
    void unit_is_a_right_identity() {
        LocalDate a = TEST_DATE;

        Function<LocalDate, Identity<LocalDate>> unit = Identity::unit;

        Function<LocalDate, Identity<String>> f = (LocalDate date) -> Identity.unit(date.format(DateTimeFormatter.ISO_DATE));

        Identity<String> f_a = f.apply(a);

        Identity<String> result = unit.apply(a).bind(f);


        assertThat(result, is(f_a));
    }
    @Test
    void bind_is_associative() {
        Identity<LocalDate> m_a = Identity.unit(TEST_DATE);


        Function<LocalDate, Identity<String>> f = (LocalDate date) -> Identity.unit(date.format(DateTimeFormatter.ISO_DATE));;
        Function<String, Identity<Integer>> g = (String string) -> Identity.unit(string.length());

        Function<Identity<LocalDate>, Identity<String>> bind_f = (Identity<LocalDate> idDate) -> idDate.bind(f);
        Function<Identity<String>, Identity<Integer>> bind_g = (Identity<String> idStr) -> idStr.bind(g);

        Function<Identity<LocalDate>, Identity<Integer>> bind_g_o_bind_f = bind_g.compose(bind_f);

        // (bind(g) o bind(f))(m_a)
        Identity<Integer> bind_g_o_bind_f__m_a = bind_g_o_bind_f.apply(m_a);


        Function<LocalDate, Identity<Integer>> bind_g_o_f = bind_g.compose(f);

        // bind(bind(g) o f))(m_a)
        Identity<Integer> bind__bind_g_o_f__m_a = m_a.bind(bind_g_o_f);

        assertThat(bind_g_o_bind_f__m_a, is(bind__bind_g_o_f__m_a));
    }

    @Test
    void how_to_use_identity() {

        String result = Identity.unit(0)
                .map(date -> LocalDate.ofEpochDay(0))
                .map(date -> date.format(DateTimeFormatter.ISO_DATE))
                .get();

        String correct = TEST_DATE.format(DateTimeFormatter.ISO_DATE);

        assertThat(result, is(correct));
    }
}