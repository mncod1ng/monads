package org.example.monad.list_monad;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListMonad {

    public <X,Y> Function<List<X>, List<Y>> fmap(Function<X, Y> f){
        return (List<X> listX) -> listX.stream().map(f).collect(Collectors.toList());
    }

    public <X> List<X> unit(X x){
        return List.of(x);
    }

    public <X> List<X> flat(List<List<X>> nestedList){
        BinaryOperator<List<X>> concat = (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
        return nestedList.stream().reduce(new ArrayList<>(), concat);
    }

}
