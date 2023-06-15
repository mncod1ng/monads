package org.example.monad_definition;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
}