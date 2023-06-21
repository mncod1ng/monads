package org.example.monad_definition;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;

class OptionalFunctorTest {
    @Test
    void example_optional_monad() {

        Optional<Integer> result = OptionalFunctor.fmap((this::getLength))
                .andThen(OptionalFunctor.fmap(length -> length % 2))
                .apply(OptionalFunctor.fmap("test"));



        assertThat(result.isPresent(), Matchers.is(true));
        assertThat(result.get(), Matchers.is(0));
    }

    private Integer getLength(String string) {
        if (string == null) {
            return null;
        }
        return string.length();
    }

    @Test
    void flatten() {
        Optional<Optional<Integer>> nested = Optional.of(Optional.of(1));
        Optional<Integer> flat = nested.flatMap(Function.identity());

        assertThat(flat.isPresent(), Matchers.is(true));
        assertThat(flat.get(),Matchers.is(1));
    }
}