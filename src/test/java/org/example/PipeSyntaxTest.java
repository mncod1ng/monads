package org.example;

import org.example.monad.try_monad.Try;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PipeSyntaxTest {

    @Test
    void test_pipe_syntax() {
        Integer result_0 = ((Function<Integer, Integer>) (Integer n) -> n + 1)
                .andThen(n -> n + 1)
                .andThen(n -> n + 1)
                .apply(1);

        assertThat(result_0, is(4));

        Integer result_1 = ((BiFunction<Integer, Integer, Integer>) Integer::sum)
                .andThen(n -> n + 1)
                .apply(4, 5);

        assertThat(result_1, is(10));


        Integer result_2 = ((Function<String, Try<String>>) Try::to)
                .andThen(tryTo -> tryTo.map(Integer::valueOf))
                .andThen(result -> result.orElse(0) * 3)
                .apply("fails");

        assertThat(result_2, is(0));
    }
}
