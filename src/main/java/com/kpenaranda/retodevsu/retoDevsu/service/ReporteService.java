package com.kpenaranda.retodevsu.retoDevsu.service;

import com.kpenaranda.retodevsu.retoDevsu.dto.ReporteDTO;
import java.time.LocalDate;

public interface ReporteService {

  ReporteDTO getReporte(Long clienteId, LocalDate fechaInicial, LocalDate fechafinal);

}
