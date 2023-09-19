package com.kpenaranda.retodevsu.retoDevsu.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import com.kpenaranda.retodevsu.retoDevsu.entity.Cuenta;
import com.kpenaranda.retodevsu.retoDevsu.repository.ClienteRepository;
import com.kpenaranda.retodevsu.retoDevsu.repository.CuentaRepository;
import com.kpenaranda.retodevsu.retoDevsu.repository.MovimientoRepository;
import com.kpenaranda.retodevsu.retoDevsu.dto.ClienteDTO;
import com.kpenaranda.retodevsu.retoDevsu.dto.CuentaDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoCuenta;
import com.kpenaranda.retodevsu.retoDevsu.utility.mappers.CuentaMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

  @Mock
  private CuentaRepository mockCuentaRepository;
  @Mock
  private CuentaMapper mockCuentaMapper;
  @Mock
  private ClienteRepository mockClienteRepository;
  @Mock
  private MovimientoRepository mockMovimientoRepository;

  private CuentaServiceImpl cuentaServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    cuentaServiceImplUnderTest = new CuentaServiceImpl(mockCuentaRepository, mockCuentaMapper,
        mockClienteRepository, mockMovimientoRepository);
  }

  @Test
  void testGetCuenta() {
    final CuentaDTO expectedResult = new CuentaDTO();
    expectedResult.setId(2L);
    expectedResult.setNumeroCuenta("5432109876");
    expectedResult.setTipoCuenta(TipoCuenta.CORRIENTE);
    final ClienteDTO cliente = new ClienteDTO();
    cliente.setIdentificacion("identificacion2");
    expectedResult.setCliente(cliente);

    final Cuenta cuenta1 = new Cuenta();
    cuenta1.setNumeroCuenta("5432109876");
    cuenta1.setTipoCuenta(TipoCuenta.CORRIENTE);
    cuenta1.setSaldoInicial(5000.0);
    cuenta1.setEstado(true);

    final Cliente cliente1 = new Cliente();
    cliente1.setIdentificacion("identificacion2");
    cuenta1.setCliente(cliente1);

    final Optional<Cuenta> cuenta = Optional.of(cuenta1);
    when(mockCuentaRepository.findById(2L)).thenReturn(cuenta);

    final CuentaDTO cuentaDTO = new CuentaDTO();
    cuentaDTO.setId(2L);
    cuentaDTO.setNumeroCuenta("5432109876");
    cuentaDTO.setTipoCuenta(TipoCuenta.CORRIENTE);

    final ClienteDTO cliente2 = new ClienteDTO();
    cliente2.setIdentificacion("identificacion2");
    cuentaDTO.setCliente(cliente2);

    final Cuenta cuenta2 = new Cuenta();
    cuenta2.setNumeroCuenta("5432109876");
    cuenta2.setTipoCuenta(TipoCuenta.CORRIENTE);
    cuenta2.setSaldoInicial(5000.0);
    cuenta2.setEstado(true);

    final Cliente cliente3 = new Cliente();
    cliente3.setIdentificacion("identificacion2");
    cuenta2.setCliente(cliente3);

    when(mockCuentaMapper.cuentaToCuentaDTO(cuenta2)).thenReturn(cuentaDTO);

    final CuentaDTO result = cuentaServiceImplUnderTest.getCuenta(2L);

    assertThat(result).isEqualTo(expectedResult);
  }


  @Test
  void testUpdateCuenta() {
    final CuentaDTO cuentaDTO = new CuentaDTO();
    cuentaDTO.setId(2L);
    cuentaDTO.setNumeroCuenta("5432109876");
    cuentaDTO.setTipoCuenta(TipoCuenta.CORRIENTE);

    final ClienteDTO cliente = new ClienteDTO();
    cliente.setIdentificacion("identificacion2");
    cuentaDTO.setCliente(cliente);

    final CuentaDTO expectedResult = new CuentaDTO();
    expectedResult.setId(2L);
    expectedResult.setNumeroCuenta("5432109876");
    expectedResult.setTipoCuenta(TipoCuenta.CORRIENTE);

    final ClienteDTO cliente1 = new ClienteDTO();
    cliente1.setIdentificacion("identificacion2");
    expectedResult.setCliente(cliente1);

    final Cuenta cuenta1 = new Cuenta();
    cuenta1.setNumeroCuenta("5432109876");
    cuenta1.setTipoCuenta(TipoCuenta.CORRIENTE);
    cuenta1.setSaldoInicial(7500.0);
    cuenta1.setEstado(true);

    final Cliente cliente2 = new Cliente();
    cliente2.setIdentificacion("identificacion2");
    cuenta1.setCliente(cliente2);

    final Optional<Cuenta> cuenta = Optional.of(cuenta1);
    when(mockCuentaRepository.findById(2L)).thenReturn(cuenta);

    final Cuenta cuenta2 = new Cuenta();
    cuenta2.setNumeroCuenta("5432109876");
    cuenta2.setTipoCuenta(TipoCuenta.CORRIENTE);
    cuenta2.setSaldoInicial(7500.0);
    cuenta2.setEstado(true);

    final Cliente cliente3 = new Cliente();
    cliente3.setIdentificacion("identificacion2");
    cuenta2.setCliente(cliente3);

    final CuentaDTO cuentaDTO1 = new CuentaDTO();
    cuentaDTO1.setId(2L);
    cuentaDTO1.setNumeroCuenta("5432109876");
    cuentaDTO1.setTipoCuenta(TipoCuenta.CORRIENTE);

    final ClienteDTO cliente4 = new ClienteDTO();
    cliente4.setIdentificacion("identificacion2");
    cuentaDTO1.setCliente(cliente4);

    when(mockCuentaMapper.cuentaDTOToCuenta(cuentaDTO1)).thenReturn(cuenta2);

    final CuentaDTO cuentaDTO2 = new CuentaDTO();
    cuentaDTO2.setId(2L);
    cuentaDTO2.setNumeroCuenta("5432109876");
    cuentaDTO2.setTipoCuenta(TipoCuenta.CORRIENTE);

    final ClienteDTO cliente5 = new ClienteDTO();
    cliente5.setIdentificacion("identificacion2");
    cuentaDTO2.setCliente(cliente5);

    final Cuenta cuenta3 = new Cuenta();
    cuenta3.setNumeroCuenta("5432109876");
    cuenta3.setTipoCuenta(TipoCuenta.CORRIENTE);
    cuenta3.setSaldoInicial(7500.0);
    cuenta3.setEstado(true);

    final Cliente cliente6 = new Cliente();
    cliente6.setIdentificacion("identificacion2");
    cuenta3.setCliente(cliente6);

    when(mockCuentaMapper.cuentaToCuentaDTO(cuenta3)).thenReturn(cuentaDTO2);

    final CuentaDTO result = cuentaServiceImplUnderTest.updateCuenta(2L, cuentaDTO);

    assertThat(result).isEqualTo(expectedResult);

    final Cuenta entity = new Cuenta();
    entity.setNumeroCuenta("5432109876");
    entity.setTipoCuenta(TipoCuenta.CORRIENTE);
    entity.setSaldoInicial(7500.0);
    entity.setEstado(true);

    final Cliente cliente7 = new Cliente();
    cliente7.setIdentificacion("identificacion2");
    entity.setCliente(cliente7);

    verify(mockCuentaRepository).save(entity);
  }
}
