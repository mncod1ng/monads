package org.example.monad.list_monad;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

class ListFunctorTest {

    @Test
    void should_lift_function() {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> result = ListFunctor.fmap((Integer integer) -> integer * integer).apply(integers);

        assertThat(result, Matchers.equalTo(List.of(1, 4, 9, 16, 25, 36, 49, 64)));
    }
}