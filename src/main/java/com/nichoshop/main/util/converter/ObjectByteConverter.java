package com.nichoshop.main.util.converter;

import org.apache.commons.lang3.SerializationUtils;
import java.io.Serializable;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Nursultan 08/17/22.
 */

@Converter
public class ObjectByteConverter implements AttributeConverter<Object, byte[]>{

  @Override
  public byte[] convertToDatabaseColumn(Object object) {
    return SerializationUtils.serialize((Serializable) object);
  }

  @Override
  public Object convertToEntityAttribute(byte[] bytes) {
    return SerializationUtils.deserialize(bytes);
  }
}