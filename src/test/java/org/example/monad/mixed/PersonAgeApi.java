package org.example.monad.mixed;

import java.util.Map;
import java.util.Optional;

public class PersonAgeApi {

    private final Map<String, String> personAgeData = Map.of("Max Mustermann", "12", "Hans Müller", "-");

    public Optional<String> findAgeForPersonByName(String name) {
        if (!personAgeData.containsKey(name)){
            return Optional.empty();
        };
        return Optional.of(personAgeData.get(name));
    }

}
