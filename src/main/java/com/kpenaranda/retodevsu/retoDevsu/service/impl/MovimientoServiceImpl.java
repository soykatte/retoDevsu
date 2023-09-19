package com.kpenaranda.retodevsu.retoDevsu.service.impl;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cuenta;
import com.kpenaranda.retodevsu.retoDevsu.entity.Movimiento;
import com.kpenaranda.retodevsu.retoDevsu.repository.ClienteRepository;
import com.kpenaranda.retodevsu.retoDevsu.repository.CuentaRepository;
import com.kpenaranda.retodevsu.retoDevsu.repository.MovimientoRepository;
import com.kpenaranda.retodevsu.retoDevsu.service.MovimientoService;
import com.kpenaranda.retodevsu.retoDevsu.dto.MovimientoDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoMovimiento;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.NoEncontradoException;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.PeticionErroneaException;
import com.kpenaranda.retodevsu.retoDevsu.utility.mappers.MovimientoMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
public class MovimientoServiceImpl implements MovimientoService {
  
  private final String SALDO_NO_DISPONIBLE = "Saldo no disponible";
  private final String CUPO_DIARIO_EXCEDIDO = "Cupo diario excedido";
  private final MovimientoRepository movimientoRepository;
  private final MovimientoMapper movimientoMapper;
  private final CuentaRepository cuentaRepository;
  private final ClienteRepository clienteRepository;


  @Autowired
  public MovimientoServiceImpl(MovimientoRepository movimientoRepository,
      MovimientoMapper movimientoMapper, CuentaRepository cuentaRepository,
      ClienteRepository clienteRepository) {
    this.movimientoRepository = movimientoRepository;
    this.movimientoMapper = movimientoMapper;
    this.cuentaRepository = cuentaRepository;
    this.clienteRepository = clienteRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<MovimientoDTO> getMovimientos() {
    return movimientoRepository.findAll().stream().map(movimientoMapper::movimientoToMovimientoDTO).collect(
        Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public MovimientoDTO getMovimiento(Long id) {
    return movimientoRepository.findById(id).map(movimientoMapper::movimientoToMovimientoDTO)
        .orElse(null);
  }

  @Override
  @Transactional
  public MovimientoDTO createMovimiento(MovimientoDTO movimientoDTO) {
    Optional<Cuenta> cuentaOpt = cuentaRepository.findByNumeroCuenta(
        movimientoDTO.getCuenta().getNumeroCuenta());
    double saldoTotal = 0;
    if (cuentaOpt.isPresent()) {
      if (cuentaOpt.get().getMovimientos().isEmpty()) {
        saldoTotal = hacerMovimiento(movimientoDTO.getTipoMovimiento().getValue(),
            cuentaOpt.get().getSaldoInicial(), movimientoDTO.getValor());
      } else {
        double saldoUltimoMovimiento = getUltimoMovimiento(cuentaOpt.get().getMovimientos());
        saldoTotal = hacerMovimiento(movimientoDTO.getTipoMovimiento().toString(),
            saldoUltimoMovimiento, movimientoDTO.getValor());
      }
      validateSaldo(saldoTotal);

      Movimiento movimiento = movimientoMapper.movimientoDTOToMovimiento(movimientoDTO);
      movimiento.setFecha(LocalDate.now());
      movimiento.setCuenta(cuentaOpt.get());
      movimiento.setSaldo(saldoTotal);

      if (validarSaldoExcedido(cuentaOpt.get().getMovimientos(), movimiento)) {
        log.warn(CUPO_DIARIO_EXCEDIDO);
        throw new PeticionErroneaException(CUPO_DIARIO_EXCEDIDO);
      }

      final Movimiento nuevoMovimiento = movimientoRepository.save(movimiento);
      log.info("Movimiento realizado!");
      return movimientoMapper.movimientoToMovimientoDTO(nuevoMovimiento);
    }
    log.warn("Cuenta no encontrada");
    throw new NoEncontradoException("Cuenta no encontrada");
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    Optional<Movimiento> movimientoOpt = movimientoRepository.findById(id);
    if (movimientoOpt.isPresent()) {
      movimientoRepository.deleteById(id);
      log.info("Movimiento eliminado!");
    } else {
      log.warn("Movimiento no encontrado");
      throw new NoEncontradoException("Movimiento no encontrado");
    }
  }

  @Override
  @Transactional
  public MovimientoDTO updateMovimiento(Long id, MovimientoDTO movimientoDTO) {
    Optional<Movimiento> movimientoOpt = movimientoRepository.findById(id);
    if (movimientoOpt.isPresent()) {
      Movimiento movimientoActual = movimientoOpt.get();
      double saldoTotal = 0;
      double saldo = getSaldo(movimientoDTO.getCuenta().getNumeroCuenta(), movimientoActual);

      saldoTotal = hacerMovimiento(movimientoDTO.getTipoMovimiento().getValue(), saldo,
          movimientoDTO.getValor());

      validateSaldo(saldoTotal);

      movimientoActual.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
      movimientoActual.setValor(movimientoDTO.getValor());
      movimientoActual.setSaldo(saldoTotal);

      movimientoRepository.save(movimientoActual);
      log.info("Movimiento actualizado");
      return movimientoMapper.movimientoToMovimientoDTO(movimientoActual);
    }
    log.warn("Movimiento no encontrado");
    throw new NoEncontradoException("Movimiento no encontrado");
  }

  @Override
  @Transactional
  public MovimientoDTO actualizacionParcialByFields(Long id, Map<String, Object> fields) {
    Optional<Movimiento> movimientoOpt = movimientoRepository.findById(id);
    if (movimientoOpt.isPresent()) {
      Movimiento movimientoActual = movimientoOpt.get();
      double saldoTotal = 0;
      double saldo = getSaldo(movimientoActual.getCuenta().getNumeroCuenta(), movimientoActual);
      populateMapToMovimiento(movimientoActual, fields);
      saldoTotal = hacerMovimiento(movimientoActual.getTipoMovimiento().getValue(), saldo,
          movimientoActual.getValor());
      validateSaldo(saldoTotal);

      movimientoActual.setSaldo(saldoTotal);
      movimientoRepository.save(movimientoActual);
      log.info("Movimiento actualizado");
      return movimientoMapper.movimientoToMovimientoDTO(movimientoActual);
    } else {
      log.warn("Movimiento no encontrado");
      throw new NoEncontradoException("Movimiento no encontrado");
    }
  }

  private double getSaldo(String numeroCuenta, Movimiento movimiento) {
    Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta).get();
    double saldo = getUltimoMovimiento(cuenta.getMovimientos());
      if (TipoMovimiento.RETIRO.getValue().equals(movimiento.getTipoMovimiento().getValue())) {
        saldo += movimiento.getValor();
      } else if (TipoMovimiento.DEPOSITO.getValue().equals(movimiento.getTipoMovimiento().getValue())) {
        saldo -= movimiento.getValor();
      }
    return saldo;
  }

  private void populateMapToMovimiento(Movimiento movimientoActual, Map<String, Object> fields) {
    fields.forEach((k, v) -> {
      switch (k) {
        case "tipoMovimiento":
          movimientoActual.setTipoMovimiento(movimientoMapper.stringToTipoMovimiento(v.toString()));
          break;
        case "valor":
          movimientoActual.setValor(Double.parseDouble(v + ""));
          break;
      }
    });
  }

  private double hacerMovimiento(String tipoMovimiento, double saldo, double valor) {
    double saldoTotal = 0;
    if (TipoMovimiento.RETIRO.getValue().equals(tipoMovimiento)) {
      saldoTotal = saldo - valor;
    } else if (TipoMovimiento.DEPOSITO.getValue().equals(tipoMovimiento)) {
      saldoTotal = saldo + valor;
    }
    return saldoTotal;
  }

  public double getUltimoMovimiento(List<Movimiento> movimientos) {
    Movimiento movimiento = movimientos.stream().reduce((first, second) -> second).orElse(null);
    return Objects.nonNull(movimiento) ? movimiento.getSaldo() : 0;
  }

  private boolean validarSaldoExcedido(List<Movimiento> movimientoList, Movimiento movimiento) {
    AtomicReference<Double> saldoMaxDia = new AtomicReference<>((double) 0);
    List<Movimiento> movimientoLista = movimientoList.stream()
        .filter(m -> m.getFecha().isEqual(movimiento.getFecha())
            && TipoMovimiento.RETIRO.getValue().equals(m.getTipoMovimiento().getValue())).toList();

    movimientoLista.forEach(mov -> {
      saldoMaxDia.set(saldoMaxDia.get() + mov.getValor());
    });

    if (TipoMovimiento.RETIRO.getValue().equals(movimiento.getTipoMovimiento().getValue())){
      saldoMaxDia.set(saldoMaxDia.get() + movimiento.getValor());
    }
    return saldoMaxDia.get() >= 1000 ? Boolean.TRUE : Boolean.FALSE;

  }

  private void validateSaldo(double saldoTotal){
    if (saldoTotal < 0) {
      log.warn(SALDO_NO_DISPONIBLE);
      throw new PeticionErroneaException(SALDO_NO_DISPONIBLE);
    }
  }
}
