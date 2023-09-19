package com.kpenaranda.retodevsu.retoDevsu.service.impl;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import com.kpenaranda.retodevsu.retoDevsu.entity.Cuenta;
import com.kpenaranda.retodevsu.retoDevsu.entity.Movimiento;
import com.kpenaranda.retodevsu.retoDevsu.service.ClienteService;
import com.kpenaranda.retodevsu.retoDevsu.service.MovimientoService;
import com.kpenaranda.retodevsu.retoDevsu.service.ReporteService;
import com.kpenaranda.retodevsu.retoDevsu.dto.CuentaReporteDTO;
import com.kpenaranda.retodevsu.retoDevsu.dto.MovimientoReporteDTO;
import com.kpenaranda.retodevsu.retoDevsu.dto.ReporteDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoMovimiento;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.NoEncontradoException;
import com.kpenaranda.retodevsu.retoDevsu.utility.mappers.MovimientoMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReporteServiceImpl implements ReporteService {
  private final ClienteService clienteService;
  private final MovimientoService movimientoService;
  private final MovimientoMapper movimientoMapper;


  @Autowired
  public ReporteServiceImpl(ClienteService clienteService, MovimientoService movimientoService,
      MovimientoMapper movimientoMapper) {
    this.clienteService = clienteService;
    this.movimientoService = movimientoService;
    this.movimientoMapper = movimientoMapper;
  }

  @Override
  public ReporteDTO getReporte(Long clienteId, LocalDate fechaInicial, LocalDate fechafinal) {

    final Cliente cliente = clienteService.getMovByClienteId(clienteId, fechaInicial, fechafinal);

    ReporteDTO reporteDTO = new ReporteDTO();
    reporteDTO.setClienteId(cliente.getClienteId());
    reporteDTO.setNombre(cliente.getNombre());
    reporteDTO.setIdentificacion(cliente.getIdentificacion());

    List<CuentaReporteDTO> cuentaReporteDTOList = new ArrayList<>();

    for (Cuenta cuenta : cliente.getCuentas()) {
      CuentaReporteDTO cuentaReporteDTO = new CuentaReporteDTO();
      cuentaReporteDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
      cuentaReporteDTO.setTipoCuenta(cuenta.getTipoCuenta().toString());
      cuentaReporteDTO.setSaldo(cuenta.getSaldoInicial());

      List<MovimientoReporteDTO> movimientosReporteDTO = new ArrayList<>();

      double totalRetiros = cuenta.getMovimientos().stream().filter(m -> TipoMovimiento.RETIRO.getValue().equals(
          m.getTipoMovimiento().getValue())).mapToDouble(Movimiento::getValor).sum();

      double totalDepositos = cuenta.getMovimientos().stream().filter(m -> TipoMovimiento.DEPOSITO.getValue().equals(
          m.getTipoMovimiento().getValue())).mapToDouble(Movimiento::getValor).sum();

      for (Movimiento m : cuenta.getMovimientos()) {
        MovimientoReporteDTO movimientoReporteDTO = new MovimientoReporteDTO();
        movimientoReporteDTO.setFecha(m.getFecha());
        movimientoReporteDTO.setTipoMovimiento(m.getTipoMovimiento());
        movimientoReporteDTO.setValor(m.getValor());
        movimientosReporteDTO.add(movimientoReporteDTO);
      }
      cuentaReporteDTO.setMovimientos(movimientosReporteDTO);
      cuentaReporteDTO.setTotalRetiros(totalRetiros);
      cuentaReporteDTO.setTotalDepositos(totalDepositos);
      cuentaReporteDTO.setSaldo(cuentaReporteDTO.getSaldo() + (totalDepositos - totalRetiros));
      cuentaReporteDTOList.add(cuentaReporteDTO);
    }
    reporteDTO.setCuentas(cuentaReporteDTOList);
    if (cliente == null) {
      log.warn("Cliente no encontrado!");
      throw new NoEncontradoException("Cliente no encontrado!");
    }
    log.info("Reporte Generado!");
    return reporteDTO;
  }


}
