package com.kpenaranda.retodevsu.retoDevsu.utility.mappers;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cuenta;
import com.kpenaranda.retodevsu.retoDevsu.dto.CuentaDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoCuenta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface CuentaMapper {

  CuentaMapper MAPPER = Mappers.getMapper(CuentaMapper.class);

  Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO);

  CuentaDTO cuentaToCuentaDTO(Cuenta cuenta);

  default String tipoCuentaToString(TipoCuenta tipoCuenta) {
    return tipoCuenta.getValue();
  }

  default TipoCuenta stringToTipoCuenta(String tipoCuenta) {
    return TipoCuenta.fromValue(tipoCuenta);
  }

  default Boolean stringToBoolean(String valor) {
    return Boolean.valueOf(valor);
  }
}