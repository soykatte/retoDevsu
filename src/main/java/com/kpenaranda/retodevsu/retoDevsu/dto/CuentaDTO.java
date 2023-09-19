package com.kpenaranda.retodevsu.retoDevsu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoCuenta;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaDTO {

  private Long id;

  @NotEmpty(message = "Número de cuenta es un campo obligatorio")
  @Pattern(regexp = "^[0-9]{6,11}$", message = "Número de cuenta inválido")
  @Column(name = "numero_de_cuenta", unique = true)
  private String numeroCuenta;

  @NotNull(message = "El tipo de cuenta es un campo obligatorio")
  @Enumerated(EnumType.STRING)
  private TipoCuenta tipoCuenta;

  @NotNull(message = "El saldo inicial es un campo obligatorio")
  private Double saldoInicial;

  private Boolean estado;

  @NotNull(message = "El cliente es un campo obligatorio")
  private ClienteDTO cliente;
}
