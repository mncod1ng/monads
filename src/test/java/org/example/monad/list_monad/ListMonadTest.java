package org.example.monad.list_monad;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ListMonadTest {

    @Test
    void should_flat_nested_list() {
        List<Integer> list1 = List.of(1, 2, 3, 4);
        List<Integer> list2 = List.of(5, 6, 7, 8);
        List<List<Integer>> nestedList = List.of(list1, list2);

        ListMonad listMonad = new ListMonad();

        List<Integer> flatted = listMonad.flat(nestedList);

        assertThat(flatted, Matchers.contains(1, 2, 3, 4, 5, 6, 7, 8));
    }
}