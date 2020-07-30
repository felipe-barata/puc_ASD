package br.com.sigo.consultoria.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContratoDTO implements Serializable {

  private Integer id;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @NotNull(message = "Data inicial é obrigatória")
  private LocalDate inicio;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @NotNull(message = "Data final é obrigatória")
  private LocalDate fim;

  @Min(value = 1, message = "Valor inválido")
  private double valor;

  @Min(value = 1, message = "Quantidade de horas inválida")
  private int horas;

}
