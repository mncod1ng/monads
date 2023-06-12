package org.example;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class UniformSubstitutionFailsTest {

    public int square(int x){
        return x*x;
    }

    @Test
    void uniform_substitution_fails_part_1() {
        Counter counter = new Counter();
        int result_1 = square(counter.increment());
        assertThat(result_1, Matchers.is(1));
    }
    @Test
    void uniform_substitution_fails_part_2() {
        Counter counter = new Counter();
        int result_2 = counter.increment() * counter.increment();
        assertThat(result_2, Matchers.is(2));
    }


}
