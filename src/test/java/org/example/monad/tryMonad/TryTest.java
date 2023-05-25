package org.example.monad.tryMonad;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TryTest {

    @Test
    void should_be_one() {

        Integer result = Try.ofThrowable(() -> Integer.valueOf("2"))
                .flatMap(n -> Try.ofThrowable(() -> n / 2))
                .orElse(0);

        assertThat(result, is(1));
    }

    @Test
    void should_be_zero_when_not_divisible_by_two() {

        Integer result = Try.ofThrowable(() -> Integer.valueOf("1"))
                .flatMap(n -> Try.ofThrowable(() -> n / 2))
                .orElse(0);

        assertThat(result, is(0));
    }


    @Test
    void should_be_zero_when_string_not_parsable_to_integer() {

        Integer result = Try.ofThrowable(() -> Integer.valueOf("blub"))
                .flatMap(n -> Try.ofThrowable(() -> n / 2))
                .orElse(0);

        assertThat(result, is(0));
    }
}
