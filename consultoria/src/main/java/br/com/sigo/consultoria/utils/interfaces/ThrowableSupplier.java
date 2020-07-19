package br.com.sigo.consultoria.utils.interfaces;

import br.com.sigo.consultoria.exceptions.ConsultoriaException;

import java.util.function.Supplier;

public interface ThrowableSupplier<T> extends Supplier<T> {

  @Override
  default T get() {
    try {
      return getThrowable();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  T getThrowable() throws ConsultoriaException;

}
