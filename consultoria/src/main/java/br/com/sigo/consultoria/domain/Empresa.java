package br.com.sigo.consultoria.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String cnpj;

  @Column(name = "nome_empresa")
  private String nomeEmpresa;

  @Column(name = "codigo_postal")
  private String codigoPostal;

  @Column(name = "codigo_pais")
  private Integer codigoPais;

  private String estado;

  private String cidade;

  private String bairro;

  private String endereco;

  private String numero;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "empresa")
  private List<Contrato> contratos;

}
