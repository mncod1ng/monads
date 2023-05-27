package org.example.monad.mixed;

import org.example.monad.tryMonad.Try;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

public class MixingMonadsTest {

    private final PersonAgeApi personAgeApi = new PersonAgeApi();


    @Test
    void syntactical_challenges_with_mixing_monads() {

        Optional<String> result = Optional.ofNullable(Try.ofThrowable(() ->
                        personAgeApi.findAgeForPersonByName("Angela Merkel"))
                .flatMap(foundAge -> Try.ofThrowable(foundAge::get))
                .orElse(null));

                /* TODO: Want to have method that returns optional
                     value present in case of success and value present
                     empty otherwise
                  */

        assertThat(result.isEmpty(), Matchers.is(true));
    }


}
