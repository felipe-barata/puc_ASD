package br.com.sigo.consultoria.dtos.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SQLParamDTO implements Serializable {

  private String name;
  private String type;
  private String val;

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return new SQLParamDTO(this.getName(), this.getType(), this.getVal());
    }
  }
}
