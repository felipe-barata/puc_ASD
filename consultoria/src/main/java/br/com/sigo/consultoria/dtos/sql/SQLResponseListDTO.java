package br.com.sigo.consultoria.dtos.sql;

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
public class SQLResponseListDTO implements Serializable {

  private List<SQLResponseDTO> listSqlResp;
}
