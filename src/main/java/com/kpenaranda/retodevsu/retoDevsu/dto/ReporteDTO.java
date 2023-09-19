package com.kpenaranda.retodevsu.retoDevsu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporteDTO {

  private List<CuentaReporteDTO> cuentas;
  private String nombre;
  private String identificacion;
  private Long clienteId;
}
