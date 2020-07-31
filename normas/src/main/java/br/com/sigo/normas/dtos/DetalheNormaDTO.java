package br.com.sigo.normas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalheNormaDTO implements Serializable {

  private int id;
  private int categoria;
  private String descCategoria;
  private int tipo;
  private String descTipo;
  private String titulo;
  private String norma;

  private List<DocumentoNormaDTO> documentos;

}
