package br.com.sigo.consultoria.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpresaDTO implements Serializable {

  private Integer id;

  @NotBlank(message = "Obrigatório informar CNPJ")
  @Length(min = 14, message = "CNPJ inválido")
  private String cnpj;

  @NotBlank(message = "Obrigatório informar nome da empresa")
  private String nomeEmpresa;

  @NotBlank(message = "Obrigatório informar o código postal")
  private String codigoPostal;

  @Min(value = 1, message = "Obrigatório informar o código do país")
  private Integer codigoPais;

  @NotBlank(message = "Obrigatório informar o estado")
  private String estado;

  @NotBlank(message = "Obrigatório informar a cidade")
  private String cidade;

  @NotBlank(message = "Obrigatório informar o bairro")
  private String bairro;

  @NotBlank(message = "Obrigatório informar o endereço")
  private String endereco;

  @NotBlank(message = "Obrigatório informar o número")
  private String numero;

  @Min(value = 1, message = "Obrigatório informar uma categoria")
  private Integer categoria;

}
