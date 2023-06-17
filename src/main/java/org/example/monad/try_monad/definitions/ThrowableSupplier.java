package org.example.monad.try_monad.definitions;


public interface ThrowableSupplier<S> {
    //Lesson: If you comment me out, you get compile errors. :-)
    S get() throws Throwable;

}
