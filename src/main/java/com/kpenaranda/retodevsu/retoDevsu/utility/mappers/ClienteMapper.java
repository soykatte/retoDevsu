package com.kpenaranda.retodevsu.retoDevsu.utility.mappers;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import com.kpenaranda.retodevsu.retoDevsu.dto.ClienteDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.Genero;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface ClienteMapper {

  ClienteMapper MAPPER = Mappers.getMapper(ClienteMapper.class);

  Cliente clienteDTOToCliente(ClienteDTO clienteDTO);

  ClienteDTO clienteToClienteDTO(Cliente cliente);

  default String generoToString(Genero genero) {
    return genero.getValue();
  }

  default Genero stringToGenero(String genero) {
    return Genero.fromValue(genero);
  }

  default String booleanToString(Boolean estado) {
    return estado.toString();
  }

  default Boolean stringToBoolean(String estado) {
    return Boolean.valueOf(estado);
  }

}
