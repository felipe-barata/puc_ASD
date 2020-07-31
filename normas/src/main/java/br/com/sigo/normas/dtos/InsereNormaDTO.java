package br.com.sigo.normas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsereNormaDTO implements Serializable {

  private int id;
  private int categoria;
  private int tipo;
  private String titulo;
  private String norma;
}
