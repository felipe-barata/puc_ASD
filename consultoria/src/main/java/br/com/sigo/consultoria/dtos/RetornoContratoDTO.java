package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetornoContratoDTO implements Serializable {

  private int id;

  private LocalDate inicio;

  private LocalDate fim;

  private double valor;

  private int horas;

  private int horasContabilizadas;

  private List<ListaArquivosContratosDTO> relatorios;
}
