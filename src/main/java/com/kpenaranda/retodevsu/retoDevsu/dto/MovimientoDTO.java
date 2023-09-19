package com.kpenaranda.retodevsu.retoDevsu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoMovimiento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientoDTO {

  private Long id;

  @NotNull(message = "El tipo de movimiento es un campo obligatorio")
  @Enumerated(EnumType.STRING)
  private TipoMovimiento tipoMovimiento;

  @NotNull(message = "El valor es un campo obligatorio")
  private Double valor;

  private CuentaDTO cuenta;

  private Double saldo;

}
