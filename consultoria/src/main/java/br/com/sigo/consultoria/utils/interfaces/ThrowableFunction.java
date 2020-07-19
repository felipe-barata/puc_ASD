package br.com.sigo.consultoria.utils.interfaces;

import java.util.function.Function;

import br.com.sigo.consultoria.exceptions.ConsultoriaException;

@FunctionalInterface
public interface ThrowableFunction<T, R> extends Function<T, R> {

    @Override
    default R apply(T t) {
        try {
            return applyThrowable(t);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    R applyThrowable(T t) throws ConsultoriaException;
}
