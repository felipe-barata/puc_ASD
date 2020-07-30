package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginacaoDTO implements Serializable {

  private int pagina;

  private int qtdRegistros;

}
