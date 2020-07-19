package br.com.sigo.consultoria.utils.interfaces;

import br.com.sigo.consultoria.exceptions.ConsultoriaException;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowableConsumer<T> extends Consumer<T> {

  @Override
  default void accept(T t) {
    try {
      acceptThrowable(t);
    } catch (Exception ex) {
      new RuntimeException(ex);
    }
  }

  void acceptThrowable(T t) throws ConsultoriaException;

}
