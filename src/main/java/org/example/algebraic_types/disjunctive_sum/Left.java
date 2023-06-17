package org.example.algebraic_types.disjunctive_sum;

import java.util.function.Function;

public final class Left<L, R> extends Either<L, R> {
    private final L left;

    Left(L left) {
        this.left = left;
    }

    @Override
    public <L1, R1> Either<L1, R1> map(Function<L, L1> onLeft, Function<R, R1> onRight) {
        return new Left<>(onLeft.apply(left));
    }

    @Override
    public R getRight() {
        throw new IllegalCallerException();
    }

    @Override
    public L getLeft() {
        return left;
    }
}
