package com.kpenaranda.retodevsu.retoDevsu.service.impl;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import com.kpenaranda.retodevsu.retoDevsu.entity.Movimiento;
import com.kpenaranda.retodevsu.retoDevsu.repository.ClienteRepository;
import com.kpenaranda.retodevsu.retoDevsu.service.ClienteService;
import com.kpenaranda.retodevsu.retoDevsu.utility.BCrypt;
import com.kpenaranda.retodevsu.retoDevsu.dto.ClienteDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.NoEncontradoException;
import com.kpenaranda.retodevsu.retoDevsu.utility.exceptions.YaExisteException;
import com.kpenaranda.retodevsu.retoDevsu.utility.mappers.ClienteMapper;
import java.lang.reflect.Field;
import java.time.LocalDate;
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
public class ClienteServiceImpl implements ClienteService {
  
  private final ClienteRepository clienteRepository;
  private final ClienteMapper clienteMapper;

  @Autowired
  public ClienteServiceImpl(
      ClienteRepository clienteRepository,
      ClienteMapper clienteMapper) {
    this.clienteRepository = clienteRepository;
    this.clienteMapper = clienteMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ClienteDTO> getClientes() {
    List<Cliente> clientes = clienteRepository.findAll();
    return clientes.stream().map(clienteMapper::clienteToClienteDTO).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public ClienteDTO getCliente(Long clienteId) {
    return clienteRepository.findById(clienteId).map(clienteMapper::clienteToClienteDTO)
        .orElse(null);
  }

  @Override
  @Transactional
  public ClienteDTO createCliente(ClienteDTO clienteDTO) {
    Optional<Cliente> clienteOpt = clienteRepository.findByIdentificacion(
        clienteDTO.getIdentificacion());
    if (clienteOpt.isPresent()) {
      log.warn("Cliente ya existe");
      throw new YaExisteException("Cliente ya existe");
    }
    Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
    cliente.setPassword(BCrypt.hashpw(cliente.getPassword(), BCrypt.gensalt()));
    final Cliente clienteCreated = clienteRepository.save(cliente);
    log.info("Cliente creado!");
    return clienteMapper.clienteToClienteDTO(clienteCreated);
  }

  @Override
  @Transactional
  public void deleteById(Long clienteId) {
    clienteRepository.findById(clienteId)
        .orElseThrow(() -> new NoEncontradoException("Cliente no encontrado"));
    clienteRepository.deleteById(clienteId);
    log.info("Cliente eliminado!");
  }

  @Override
  @Transactional
  public ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO) {
    Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
      Cliente clienteActualizado = clienteOpt.get();
      clienteActualizado.setNombre(cliente.getNombre());
      clienteActualizado.setGenero(cliente.getGenero());
      clienteActualizado.setEdad(cliente.getEdad());
      clienteActualizado.setIdentificacion(cliente.getIdentificacion());
      clienteActualizado.setDireccion(cliente.getDireccion());
      clienteActualizado.setTelefono(cliente.getTelefono());
      clienteActualizado.setPassword(BCrypt.hashpw(cliente.getPassword(), BCrypt.gensalt()));
      clienteActualizado.setEstado(cliente.getEstado().booleanValue());
      clienteActualizado.setCuentas(cliente.getCuentas());
      clienteRepository.save(clienteActualizado);
      log.info("Cliente actualizado!");
      return clienteMapper.clienteToClienteDTO(clienteActualizado);
    } else {
      log.warn("Cliente no encontrado");
      throw new NoEncontradoException("Cliente no encontrado");
    }
  }

  @Override
  @Transactional
  public ClienteDTO actualizacionParcialByFields(Long clienteId, Map<String, Object> fields) {
    Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      fields.forEach((key, value) -> {
        if (key.equals("genero")) {
          value = clienteMapper.stringToGenero(value.toString());
        }
        if (key.equals("estado")) {
          value = clienteMapper.stringToBoolean(value.toString());
        }
        Field field = ReflectionUtils.findField(Cliente.class, key);
        field.setAccessible(true);
        ReflectionUtils.setField(field, clienteOpt.get(), value);
      });
      clienteOpt.get()
          .setPassword(BCrypt.hashpw(clienteOpt.get().getPassword(), BCrypt.gensalt()));
      Cliente cliente = clienteRepository.save(clienteOpt.get());
      ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente);
      log.info("Cliente parcialmente actualizado!");
      return clienteDTO;
    } else {
      log.warn("Cliente no encontrado");
      throw new NoEncontradoException("Cliente no encontrado");
    }
  }

  @Override
  public Cliente getMovByClienteId(Long clienteId, LocalDate fechaInicial,
      LocalDate fechaFinal) {
    final Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
    if (clienteOpt.isPresent()) {
      Cliente finalC = clienteOpt.get();
      finalC.getCuentas().forEach(c -> {
        List<Movimiento> movimientoList = c.getMovimientos().stream()
            .filter(m -> m.getFecha().isEqual(fechaInicial) || (m.getFecha().isAfter(fechaInicial)
                && m.getFecha().isBefore(fechaFinal)) || m.getFecha().isEqual(fechaFinal))
            .toList();
        c.setMovimientos(movimientoList);
      });
      log.info("Listado de movimientos obtenido!");
      return finalC;
    } else {
      log.warn("Cliente no encontrado");
      throw new NoEncontradoException("Cliente no encontrado");
    }
  }
}