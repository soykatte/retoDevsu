package com.kpenaranda.retodevsu.retoDevsu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoMovimiento;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientoReporteDTO {

  private TipoMovimiento tipoMovimiento;
  private Double valor;
  private LocalDate fecha;
}

