package org.example.monad_definition;

import java.util.Optional;
import java.util.function.Function;

public class OptionalFlatten<T> implements Function<Optional<Optional<T>>, Optional<T>> {
    @Override
    public Optional<T> apply(Optional<Optional<T>> optOpt) {
        return optOpt.orElse(Optional.empty());
    }
}
