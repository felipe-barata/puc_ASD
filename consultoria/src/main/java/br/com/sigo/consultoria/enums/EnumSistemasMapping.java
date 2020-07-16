package br.com.sigo.consultoria.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EnumSistemasMapping implements AttributeConverter<EnumSistemas, Integer> {

  @Override
  public Integer convertToDatabaseColumn(EnumSistemas enumInterface) {
    if (enumInterface == null) {
      return null;
    }
    return enumInterface.getCodigo();
  }

  @Override
  public EnumSistemas convertToEntityAttribute(Integer integer) {
    if (integer == null || integer == 0) {
      return null;
    }

    return EnumSistemas.getEnum(integer);
  }
}
