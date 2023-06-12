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
    void syntactical_challenges_with_mixing_monads() {

        Optional<LocalDate> result = Optional.ofNullable(
                Try.to(() -> internationalPlantNamesIndexApi.findDateByScientificName("Monstera acreana"))
                        .flatMap(anyAge -> Try.to(anyAge::get))
                        .flatMap(anyAge -> Try.to(() -> LocalDate.parse(anyAge, DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH))))
                        .orElse(null)
        );

                /* TODO: Want to have method that returns optional
                     value present in case of success and value present;
                     empty otherwise
                  */

        assertThat(result.isEmpty(), Matchers.is(true));
    }


}
