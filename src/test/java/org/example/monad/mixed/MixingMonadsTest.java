package org.example.monad.mixed;

import org.example.monad.try_monad.Try;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

public class MixingMonadsTest {

    private final InternationalPlantNamesIndexApi internationalPlantNamesIndexApi = new InternationalPlantNamesIndexApi();


    @Test
    void flatMap_flats_nested_monads() {
        Optional<Integer> optional1 = Optional.of(1);
        Optional<Integer> optional2 = Optional.of(2);

        Optional<Integer> result = optional1.flatMap(value1 -> optional2.map(value2 -> value1 + value2));

        assertThat(result.get(), Matchers.is(3));
    }

    @Test
    void syntactical_challenges_with_mixing_monads() {

        Optional<LocalDate> result = Optional.ofNullable(
                Try.to(() -> internationalPlantNamesIndexApi.findDateByScientificName("Monstera acreana"))
                        .flatMap(anyAge -> Try.to(anyAge::get))
                        .flatMap(anyAge -> Try.to(() -> LocalDate.parse(anyAge, DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH))))
                        .doCatch(fail -> {
                            //TODO error handling
                            return null;
                        })
        );

        assertThat(result.isEmpty(), Matchers.is(true));
    }

    @Test
    void with_functor() {

        Optional<LocalDate> result = Optional.ofNullable(
                Try.to(internationalPlantNamesIndexApi::findDateByScientificName)
                        .thenTryTo(Optional::get)
                        .thenTryTo(anyAge -> LocalDate.parse(anyAge, DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)))
                        .tryApply("Monstera acreana")
                        .doCatch(fail -> {
                            //TODO error handling
                            return null;
                        })
        );


        assertThat(result.isEmpty(), Matchers.is(true));
    }


}
