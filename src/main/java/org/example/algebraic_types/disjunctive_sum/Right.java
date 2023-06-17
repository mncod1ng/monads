package org.example.algebraic_types.disjunctive_sum;

import java.util.function.Function;

public final class Right<X, Y> extends Either<X, Y> {

    private final Y right;

    Right(Y right) {
        this.right = right;
    }

    @Override
    public <X1, Y1> Either<X1, Y1> map(Function<X, X1> onLeft, Function<Y, Y1> onRight) {
        return new Right<>(onRight.apply(right));
    }

    @Override
    public Y getRight() {
        return right;
    }

    @Override
    public X getLeft() {
        throw new IllegalCallerException();
    }
}
