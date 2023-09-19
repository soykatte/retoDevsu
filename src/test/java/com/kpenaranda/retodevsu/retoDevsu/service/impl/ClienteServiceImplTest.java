package com.kpenaranda.retodevsu.retoDevsu.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import com.kpenaranda.retodevsu.retoDevsu.entity.Cuenta;
import com.kpenaranda.retodevsu.retoDevsu.entity.Movimiento;
import com.kpenaranda.retodevsu.retoDevsu.repository.ClienteRepository;
import com.kpenaranda.retodevsu.retoDevsu.dto.ClienteDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.Genero;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.YaExisteException;
import com.kpenaranda.retodevsu.retoDevsu.utility.mappers.ClienteMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

  @Mock
  private ClienteRepository mockClienteRepository;
  @Mock
  private ClienteMapper mockClienteMapper;

  private ClienteServiceImpl clienteServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    clienteServiceImplUnderTest = new ClienteServiceImpl(mockClienteRepository, mockClienteMapper);
  }

  @Test
  void testGetClientes_ClienteRepositoryReturnsNoItems() {
    when(mockClienteRepository.findAll()).thenReturn(Collections.emptyList());
    final List<ClienteDTO> result = clienteServiceImplUnderTest.getClientes();
    assertThat(result).isEqualTo(Collections.emptyList());
  }


  @Test
  void testCreateCliente_ThrowsYaExiste() {
    final ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setClienteId(2L);
    clienteDTO.setNombre("María Rodríguez");
    clienteDTO.setGenero(Genero.FEMENINO);
    clienteDTO.setEdad(25);
    clienteDTO.setIdentificacion("identificacion2");
    clienteDTO.setDireccion("Calle 123");
    clienteDTO.setTelefono("0987654321");
    clienteDTO.setPassword("clave456");
    clienteDTO.setEstado(true);

    final Cliente cliente = new Cliente();
    cliente.setNombre("María Rodríguez");
    cliente.setGenero(Genero.FEMENINO);
    cliente.setEdad(25);
    cliente.setIdentificacion("identificacion2");
    cliente.setDireccion("Calle 123");
    cliente.setTelefono("0987654321");
    cliente.setPassword("clave456");
    cliente.setEstado(true);

    final Cuenta cuenta = new Cuenta();
    final Movimiento movimiento = new Movimiento();
    movimiento.setFecha(LocalDate.of(2023, 9, 15));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));
    final Optional<Cliente> clienteOptional = Optional.of(cliente);
    when(mockClienteRepository.findByIdentificacion("identificacion2")).thenReturn(clienteOptional);

    assertThatThrownBy(() -> clienteServiceImplUnderTest.createCliente(clienteDTO))
        .isInstanceOf(YaExisteException.class);
  }


  @Test
  void testDeleteById() {
    final Cliente cliente = new Cliente();
    cliente.setNombre("Ana López");
    cliente.setGenero(Genero.FEMENINO);
    cliente.setEdad(30);
    cliente.setIdentificacion("identificacion3");
    cliente.setDireccion("Avenida 456");
    cliente.setTelefono("0987654321");
    cliente.setPassword("clave789");
    cliente.setEstado(false);

    final Cuenta cuenta = new Cuenta();
    final Movimiento movimiento = new Movimiento();
    movimiento.setFecha(LocalDate.of(2021, 8, 10));
    cuenta.setMovimientos(List.of(movimiento));
    cliente.setCuentas(List.of(cuenta));

    final Optional<Cliente> clienteOptional = Optional.of(cliente);
    when(mockClienteRepository.findById(3L)).thenReturn(clienteOptional);

    clienteServiceImplUnderTest.deleteById(3L);

    verify(mockClienteRepository).deleteById(3L);
  }


  @Test
  void testGetMovByClienteId() {
    final Cliente expectedResult = new Cliente();
    expectedResult.setNombre("María Rodríguez");
    expectedResult.setGenero(Genero.FEMENINO);
    expectedResult.setEdad(28);
    expectedResult.setIdentificacion("identificacion2");
    expectedResult.setDireccion("Calle 123");
    expectedResult.setTelefono("0987654321");
    expectedResult.setPassword("clave456");
    expectedResult.setEstado(false);

    final Cuenta cuenta = new Cuenta();
    final Movimiento movimiento = new Movimiento();
    movimiento.setFecha(LocalDate.of(2023, 6, 15));
    cuenta.setMovimientos(List.of(movimiento));
    expectedResult.setCuentas(List.of(cuenta));

    final Cliente cliente = new Cliente();
    cliente.setNombre("María Rodríguez");
    cliente.setGenero(Genero.FEMENINO);
    cliente.setEdad(28);
    cliente.setIdentificacion("identificacion2");
    cliente.setDireccion("Calle 123");
    cliente.setTelefono("0987654321");
    cliente.setPassword("clave456");
    cliente.setEstado(false);

    final Cuenta cuenta1 = new Cuenta();
    final Movimiento movimiento1 = new Movimiento();
    movimiento1.setFecha(LocalDate.of(2022, 3, 15));
    cuenta1.setMovimientos(List.of(movimiento1));
    cliente.setCuentas(List.of(cuenta1));

    final Optional<Cliente> clienteOptional = Optional.of(cliente);
    when(mockClienteRepository.findById(2L)).thenReturn(clienteOptional);

    final Cliente result = clienteServiceImplUnderTest.getMovByClienteId(2L,
        LocalDate.of(2022, 3, 15), LocalDate.of(2022, 3, 15));

    assertThat(result).isEqualTo(expectedResult);
  }
}
