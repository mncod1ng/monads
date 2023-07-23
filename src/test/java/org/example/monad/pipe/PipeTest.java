package org.example.monad.pipe;

import org.example.monad.pipe_monad.Pipe;
import org.example.monad.try_monad.Try;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PipeTest {

    @Test
    void test_map() {
        Integer result = Pipe.of((String n) -> n)
                .map(Try::to)
                .map(tryTo -> tryTo.map(Integer::valueOf))
                .map(n -> n.orElse(0) * 3)
                .get("fails");

        assertThat(result, is(0));
    }

    @Test
    void test_transport() {
        Integer result = Pipe.of((String n) -> Integer.valueOf(n))
                .transport(Try::to)
                .map(n -> n.orElse(0) * 3)
                .get("fails");

        assertThat(result, is(0));
    }


    @Test
    void test_rtimes() {
        Integer result = Pipe.of((Integer n) -> n)
                .rchain(
                        n -> 2 * n,
                        n -> 3 * n,
                        n -> 5 * n,
                        n -> 7 * n
                )
                .get(1);

        assertThat(result, is(210));
    }
}
