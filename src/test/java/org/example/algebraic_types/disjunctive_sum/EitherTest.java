package org.example.algebraic_types.disjunctive_sum;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

class EitherTest {

    public static final String FIDO = "Fido";
    public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

    @Test
    void count_symbols_without_blanks_single_word() {
        Function<String, Either<List<Integer>, Integer>> wordLengths = Either.from(
                (String string) ->
                        Arrays.stream(string.split("\\w+"))
                                .map(String::trim)
                                .filter(String::isBlank)
                                .map(String::length)
                                .collect(Collectors.toList()),
                String::length,
                (String string) -> string.contains(" ")
        );

        Integer result = wordLengths.apply(FIDO)
                .leftMap(it -> it.stream().reduce(0, Integer::sum))
                .byExhaustion(Function.identity(), Function.identity());

        assertThat(result, Matchers.is(4));
    }

    @Test
    void count_symbols_without_blanks_text_body() {
        Function<String, Either<List<Integer>, Integer>> wordLengths = Either.from(
                (String string) ->
                        Arrays.stream(string.split(" "))
                                .map(String::trim)
                                .map(String::length)
                                .collect(Collectors.toList()),
                String::length,
                (String string) -> string.contains(" ")
        );

        Integer result = wordLengths.apply(LOREM_IPSUM)
                .leftMap(it -> it.stream().reduce(0, Integer::sum))
                .byExhaustion(Function.identity(), Function.identity());

        assertThat(result, Matchers.is(132));
    }
}