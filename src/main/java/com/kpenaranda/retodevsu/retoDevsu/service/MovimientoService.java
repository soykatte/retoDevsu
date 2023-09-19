package com.kpenaranda.retodevsu.retoDevsu.service;

import com.kpenaranda.retodevsu.retoDevsu.entity.Movimiento;
import com.kpenaranda.retodevsu.retoDevsu.dto.MovimientoDTO;
import java.util.List;
import java.util.Map;

public interface MovimientoService {

  List<MovimientoDTO> getMovimientos();

  MovimientoDTO getMovimiento(Long id);

  MovimientoDTO createMovimiento(MovimientoDTO movimientoDTO);

  MovimientoDTO updateMovimiento(Long id, MovimientoDTO movimientoDTO);

  MovimientoDTO actualizacionParcialByFields(Long id, Map<String, Object> fields);

  void deleteById(Long id);

  double getUltimoMovimiento(List<Movimiento> movimientos);

}
