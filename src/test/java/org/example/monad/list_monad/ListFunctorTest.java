package org.example.monad.list_monad;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

class ListFunctorTest {

    @Test
    void should_lift_functions() {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> result =
                ListFunctor.fmap((Integer integer) -> integer * integer)
                        .andThen(ListFunctor.fmap((Integer integer) -> integer % 2))
                        .apply(integers);

        assertThat(result, Matchers.equalTo(List.of(1, 0, 1, 0, 1, 0, 1, 0)));
    }
}