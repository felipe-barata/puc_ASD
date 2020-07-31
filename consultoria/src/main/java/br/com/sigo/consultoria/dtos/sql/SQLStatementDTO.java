package br.com.sigo.consultoria.dtos.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SQLStatementDTO implements Serializable {

  private int idSistema;
  private String chave; //select pre determinado, salvo em um properties no modulo de gestao

  private String dataSource;

  private String sqlStmt; //select customizado

  private List<SQLParamDTO> selectColumns;
  private List<SQLParamDTO> whereColumns;

  private long pageNumber;
  private long fetchSize;

}
