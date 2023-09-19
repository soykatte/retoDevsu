package com.kpenaranda.retodevsu.retoDevsu.utility.mappers;

import com.kpenaranda.retodevsu.retoDevsu.entity.Movimiento;
import com.kpenaranda.retodevsu.retoDevsu.dto.MovimientoDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoMovimiento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface MovimientoMapper {

  MovimientoMapper MAPPER = Mappers.getMapper(MovimientoMapper.class);

  Movimiento movimientoDTOToMovimiento(MovimientoDTO movimientoDTO);

  MovimientoDTO movimientoToMovimientoDTO(Movimiento movimiento);

  default String tipoMovimientoToString(TipoMovimiento tipoMovimiento) {
    return tipoMovimiento.getValue();
  }

  default TipoMovimiento stringToTipoMovimiento(String tipoMovimiento) {
    return TipoMovimiento.fromValue(tipoMovimiento);
  }

}
