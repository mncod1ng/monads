package org.example.monad.identity_monad;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class IdentityTest {

    public static final LocalDate TEST_DATE = LocalDate.of(1970, 1, 1);

    @Test
    void how_to_use_identity() {
        //imho better than a wrapper!

        long date = 0;

        String result = Identity.unit(date)
                .map(LocalDate::ofEpochDay)
                .map(anyDate -> anyDate.format(DateTimeFormatter.ISO_DATE))
                .get();

        String correct = TEST_DATE.format(DateTimeFormatter.ISO_DATE);

        assertThat(result, is(correct));
    }
}