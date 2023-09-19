package com.kpenaranda.retodevsu.retoDevsu.service;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import com.kpenaranda.retodevsu.retoDevsu.dto.ClienteDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ClienteService {

  List<ClienteDTO> getClientes();

  ClienteDTO getCliente(Long clienteId);

  ClienteDTO createCliente(ClienteDTO clienteDTO);

  ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO);

  ClienteDTO actualizacionParcialByFields(Long clienteId, Map<String, Object> fields);

  void deleteById(Long clienteId);

  Cliente getMovByClienteId(Long clienteId, LocalDate fechaInicial,
      LocalDate fechaFinal);


}