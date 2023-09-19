package com.kpenaranda.retodevsu.retoDevsu.utility.enumerators;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Genero {

  FEMENINO("Femenino"), MASCULINO("Masculino"), OTRO("Otro"), NO_RESPONDE("No Responde");

  private final String value;

  Genero(String value) {
    this.value = value;
  }

  public static Genero fromValue(String value) {
    for (Genero genero : Genero.values()) {
      if (genero.getValue().equals(value)) {
        return genero;
      }
    }
    throw new IllegalArgumentException("Género inválido: " + value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }

}
