package org.example.monad_definition;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class OptionalFunctorTest {
    @Test
    void example_optional_monad() {

        Optional<Integer> result = OptionalFunctor.fmap((this::getLength))
                .andThen(OptionalFunctor.fmap(length -> length % 2))
                .apply(OptionalFunctor.fmap("test"));


    }

    private Integer getLength(String string) {
        if (string == null) {
            return null;
        }
        return string.length();
    }
}