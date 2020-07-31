package br.com.sigo.consultoria.dtos.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SQLResponseDTO implements Serializable {

  private String sqlStmt;
  private List<List<SQLParamDTO>> selectColumns;
  private long pageNumber;
  private long pageSize;
  private long fetchSize;

}
