package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaArquivosContratosDTO implements Serializable {

  private int id;

  private String nomeArquivo;

}
