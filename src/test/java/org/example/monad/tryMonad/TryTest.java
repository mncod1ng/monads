package org.example.monad.tryMonad;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TryTest {

    public static final String IO_EXCEPTION_FALLBACK_STRING = "IOException Fallback String";

    @Test
    void should_be_one_when_divisble_by_two() {

        Integer result = Try.ofThrowable(() -> Integer.valueOf("2"))
                .flatMap(n -> Try.ofThrowable(() -> n / 2))
                .orElse(0);

        assertThat(result, is(1));
    }

    @Test
    void should_be_fallback_value_when_not_divisible_by_two() {

        Integer result = Try.ofThrowable(() -> Integer.valueOf("1"))
                .flatMap(n -> Try.ofThrowable(() -> n / 2))
                .orElse(0);

        assertThat(result, is(0));
    }

    @Test
    void should_be_fallback_value_when_string_not_parsable_to_integer() {

        Integer result = Try.ofThrowable(() -> Integer.valueOf("blub"))
                .flatMap(n -> Try.ofThrowable(() -> n / 2))
                .orElse(0);

        assertThat(result, is(0));
    }

    @Test
    void should_be_fallback_when_throws() {
        String result = Try.ofThrowable(TryTest::throwsIOException)
                .orElse(IO_EXCEPTION_FALLBACK_STRING);

        assertThat(result, is(IO_EXCEPTION_FALLBACK_STRING));
    }

    private static String throwsIOException() throws IOException {
        throw new IOException();
    }

}
