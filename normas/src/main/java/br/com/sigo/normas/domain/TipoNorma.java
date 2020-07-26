package br.com.sigo.normas.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tipo_norma")
public class TipoNorma {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String descricao;

  @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "tipoNorma")
  private List<Norma> normas;

}