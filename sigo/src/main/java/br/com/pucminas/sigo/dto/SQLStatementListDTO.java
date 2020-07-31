package br.com.pucminas.sigo.dto;

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
public class SQLStatementListDTO implements Serializable {

  private List<SQLStatementDTO> listSqlStmt;
}
