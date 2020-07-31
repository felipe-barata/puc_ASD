package br.com.sigo.normas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentoNormaDTO implements Serializable {

  private int id;

  private String referencia;

  private LocalDate data;

  private String arquivo;

}
