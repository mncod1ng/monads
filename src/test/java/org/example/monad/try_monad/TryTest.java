package org.example.monad.try_monad;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TryTest {

    public static final String IO_EXCEPTION_FALLBACK_STRING = "IOException Fallback String";
    public static final int FALLBACK_INTEGER = 123;

    @Test
    void should_be_one_when_divisble_by_two() {

        Integer result = Try.to(() -> Integer.valueOf("2"))
                .flatMap(n -> Try.to(() -> n / 2))
                .orElse(0);

        assertThat(result, is(1));
    }

    @Test
    void should_be_fallback_value_when_not_divisible_by_two() {

        Integer result = Try.to(() -> Integer.valueOf("1"))
                .flatMap(n -> Try.to(() -> n / 2))
                .orElse(0);

        assertThat(result, is(0));
    }

    @Test
    void should_be_fallback_value_when_string_not_parsable_to_integer() {

        Integer result = Try.to(() -> Integer.valueOf("blub"))
                .flatMap(n -> Try.to(() -> n / 2))
                .orElse(0);

        assertThat(result, is(0));
    }

    @Test
    void should_be_fallback_when_throws() {
        String result = Try.to(TryTest::throwsIOException)
                .orElse(IO_EXCEPTION_FALLBACK_STRING);

        assertThat(result, is(IO_EXCEPTION_FALLBACK_STRING));
    }

    @Test
    void should_be_one() {
        Integer result = Try.to(() -> Integer.valueOf("1"))
                .unless(() -> Integer.valueOf("fail"))
                .orElse(FALLBACK_INTEGER);

        assertThat(result, is(1));
    }

    @Test
    void should_be_two() {
        Integer result = Try.to(() -> Integer.valueOf("fail"))
                .orElseTry(() -> Integer.valueOf("2"))
                .orElse(FALLBACK_INTEGER);

        assertThat(result, is(2));
    }


    @Test
    void try_functor_usage() {
        Integer result = Try.to((String string) -> Integer.valueOf(string))
                .andThen(tryTo -> tryTo.map(n -> n/2))
                .apply("fails")
                .doCatch(fail -> FALLBACK_INTEGER);

        assertThat(result, is(FALLBACK_INTEGER));
    }

    @Test
    void try_another_usage() {
        Integer result = Try.to(() -> Integer.valueOf("fails"))
                .thenTry(integer -> integer / 2)
                .doCatch(fail -> FALLBACK_INTEGER);

        assertThat(result, is(FALLBACK_INTEGER));
    }

    @Test
    void try_another_usage_again() {
        Integer result = Try.to("1")
                .thenTry(Integer::valueOf)
                .thenTry(integer -> integer / 2)
                .doCatch(fail -> FALLBACK_INTEGER);

        assertThat(result, is(0));
    }

    private static String throwsIOException() throws IOException {
        throw new IOException();
    }

}
