package br.com.sigo.consultoria.utils.interfaces;

import java.util.function.BiFunction;

import br.com.sigo.consultoria.exceptions.ConsultoriaException;

@FunctionalInterface
public interface ThrowableBiFunction<T, U, R> extends BiFunction<T, U, R> {

	@Override
	default R apply(T t, U u) {
		try {
            return applyThrowable(t, u);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
	}

	R applyThrowable(T t, U u) throws ConsultoriaException;
}
