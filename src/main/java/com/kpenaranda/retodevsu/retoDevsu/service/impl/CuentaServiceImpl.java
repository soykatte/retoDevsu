package com.kpenaranda.retodevsu.retoDevsu.service.impl;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import com.kpenaranda.retodevsu.retoDevsu.entity.Cuenta;
import com.kpenaranda.retodevsu.retoDevsu.repository.ClienteRepository;
import com.kpenaranda.retodevsu.retoDevsu.repository.CuentaRepository;
import com.kpenaranda.retodevsu.retoDevsu.repository.MovimientoRepository;
import com.kpenaranda.retodevsu.retoDevsu.service.CuentaService;
import com.kpenaranda.retodevsu.retoDevsu.dto.CuentaDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.ErrorInternoException;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.NoEncontradoException;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.PeticionErroneaException;
import com.kpenaranda.retodevsu.retoDevsu.utility.mappers.CuentaMapper;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Slf4j
@Service
public class CuentaServiceImpl implements CuentaService {
  
  private final CuentaRepository cuentaRepository;
  private final CuentaMapper cuentaMapper;
  private final ClienteRepository clienteRepository;
  private final MovimientoRepository movimientoRepository;


  @Autowired
  public CuentaServiceImpl(CuentaRepository cuentaRepository,
      CuentaMapper cuentaMapper,
      ClienteRepository clienteRepository, MovimientoRepository movimientoRepository) {
    this.cuentaRepository = cuentaRepository;
    this.cuentaMapper = cuentaMapper;
    this.clienteRepository = clienteRepository;
    this.movimientoRepository = movimientoRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<CuentaDTO> getCuentas() {
    List<Cuenta> cuentas = cuentaRepository.findAll();
    return cuentas.stream().map(cuentaMapper::cuentaToCuentaDTO).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public CuentaDTO getCuenta(Long id) {
    return cuentaRepository.findById(id).map(cuentaMapper::cuentaToCuentaDTO).orElse(null);
  }

  @Override
  @Transactional
  public CuentaDTO createCuenta(CuentaDTO cuentaDTO) {
    Optional<Cliente> clienteOpt = clienteRepository.findByIdentificacion(
        cuentaDTO.getCliente().getIdentificacion());
    if (clienteOpt.isEmpty()) {
      log.warn("Verificar la identificación del cliente");
      throw new PeticionErroneaException("Verificar la identificación del cliente");
    }
    try {
      Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
      cuenta.setCliente(clienteOpt.get());
      final Cuenta nuevaCuenta = cuentaRepository.save(cuenta);
      log.info("Cuenta creada!");
      return cuentaMapper.cuentaToCuentaDTO(nuevaCuenta);
    } catch (Exception e) {
      log.warn("Error interno al crear la cuenta");
      throw new ErrorInternoException("Error interno al crear la cuenta");
    }
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    //verificar saldo antes de eliminar
    try {
      cuentaRepository.deleteById(id);
      log.info("Cuenta eliminada!");
    } catch (Exception e) {
      log.warn("Cuenta no encontrada");
      throw new NoEncontradoException("Cuenta no encontrada");
    }
  }

  @Override
  @Transactional
  public CuentaDTO updateCuenta(Long id, CuentaDTO cuentaDTO) {
    Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);
    if (cuentaOpt.isPresent()) {
      Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
      Cuenta cuentaActualizada = cuentaOpt.get();
      cuentaActualizada.setNumeroCuenta(cuenta.getNumeroCuenta());
      cuentaActualizada.setTipoCuenta(cuenta.getTipoCuenta());
      cuentaActualizada.setSaldoInicial(cuenta.getSaldoInicial());
      cuentaActualizada.setEstado(cuenta.getEstado());
      cuentaRepository.save(cuentaActualizada);
      log.info("Cuenta actualizada!");
      return cuentaMapper.cuentaToCuentaDTO(cuentaActualizada);
    } else {
      log.warn("Cuenta no encontrada!");
      throw new NoEncontradoException("Cuenta no encontrada");
    }
  }

  @Override
  @Transactional
  public CuentaDTO actualizacionParcialByFields(Long id, Map<String, Object> fields) {
    Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);
    if (cuentaOpt.isPresent()) {
      fields.forEach((key, value) -> {
        if (key.equals("tipoCuenta")) {
          value = cuentaMapper.stringToTipoCuenta(value.toString());
        }
        if (key.equals("estado")) {
          value = cuentaMapper.stringToBoolean(value.toString());
        }
        Field field = ReflectionUtils.findField(Cuenta.class, key);
        field.setAccessible(true);
        ReflectionUtils.setField(field, cuentaOpt.get(), value);
      });
      cuentaRepository.save(cuentaOpt.get());
      log.info("Cuenta actualizada!");
      CuentaDTO cuentaDTO = cuentaMapper.cuentaToCuentaDTO(cuentaOpt.get());
      return cuentaDTO;
    } else {
      log.warn("Cuenta no encontrada!");
      throw new NoEncontradoException("Cuenta no encontrada");
    }
  }
}
