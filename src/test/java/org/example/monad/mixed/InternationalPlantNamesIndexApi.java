package org.example.monad.mixed;

import java.util.Map;
import java.util.Optional;

public class InternationalPlantNamesIndexApi {

    private final Map<String, String> botanicalData = Map.of("Monstera acreana", "1914", "Osmunda regalis", "1 May 1753");

    public Optional<String> findDateByScientificName(String plantName) {
        if (!botanicalData.containsKey(plantName)){
            return Optional.empty();
        };
        return Optional.of(botanicalData.get(plantName));
    }

}
