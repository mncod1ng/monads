package org.example.algebraic_types.disjunctive_sum;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Disjunctive sum type sometimes written as Z = X | Y
 */
public abstract sealed class Either<X, Y> permits Left, Right {

    public static <T, L, R> Function<T, Either<L, R>> from(Function<T, L> onLeft, Function<T, R> onRight, Predicate<T> isOnLeft){
        return (any) -> {
            if (isOnLeft.test(any)) {
                return new Left<>(onLeft.apply(any));
            }
            return new Right<>(onRight.apply(any));
        };
    }

    public abstract <X1, Y1> Either<X1, Y1> map(Function<X, X1> onLeft, Function<Y, Y1> onRight);


    public <L1> Either<L1, Y> leftMap(Function<X, L1> onLeft) {
        return this.map(onLeft, Function.identity());
    }

    public <R1> Either<X, R1> rightMap(Function<Y, R1> onRight) {
        return this.map(Function.identity(), onRight);
    }

    public abstract Y getRight();

    public abstract X getLeft();

    public <Z> Z byExhaustion(Function<X, Z> onLeft, Function<Y, Z> onRight) {
        if (this instanceof Left<X,Y> left){
            return left.leftMap(onLeft).getLeft();
        }
        if (this instanceof Right<X,Y> right){
            return right.rightMap(onRight).getRight();
        }
        throw new RuntimeException("Ex falso quod libet");
    }
}
