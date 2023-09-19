package com.kpenaranda.retodevsu.retoDevsu.utility.mappers;

import com.kpenaranda.retodevsu.retoDevsu.dto.ClienteDTO;
import com.kpenaranda.retodevsu.retoDevsu.dto.CuentaDTO;
import com.kpenaranda.retodevsu.retoDevsu.dto.MovimientoDTO;
import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import com.kpenaranda.retodevsu.retoDevsu.entity.Cuenta;
import com.kpenaranda.retodevsu.retoDevsu.entity.Movimiento;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-18T18:17:04-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class MovimientoMapperImpl implements MovimientoMapper {

    @Override
    public Movimiento movimientoDTOToMovimiento(MovimientoDTO movimientoDTO) {
        if ( movimientoDTO == null ) {
            return null;
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setId( movimientoDTO.getId() );
        movimiento.setTipoMovimiento( movimientoDTO.getTipoMovimiento() );
        movimiento.setValor( movimientoDTO.getValor() );
        movimiento.setSaldo( movimientoDTO.getSaldo() );
        movimiento.setCuenta( cuentaDTOToCuenta( movimientoDTO.getCuenta() ) );

        return movimiento;
    }

    @Override
    public MovimientoDTO movimientoToMovimientoDTO(Movimiento movimiento) {
        if ( movimiento == null ) {
            return null;
        }

        MovimientoDTO movimientoDTO = new MovimientoDTO();

        movimientoDTO.setId( movimiento.getId() );
        movimientoDTO.setTipoMovimiento( movimiento.getTipoMovimiento() );
        movimientoDTO.setValor( movimiento.getValor() );
        movimientoDTO.setCuenta( cuentaToCuentaDTO( movimiento.getCuenta() ) );
        movimientoDTO.setSaldo( movimiento.getSaldo() );

        return movimientoDTO;
    }

    protected Cliente clienteDTOToCliente(ClienteDTO clienteDTO) {
        if ( clienteDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setClienteId( clienteDTO.getClienteId() );
        cliente.setNombre( clienteDTO.getNombre() );
        cliente.setGenero( clienteDTO.getGenero() );
        cliente.setEdad( clienteDTO.getEdad() );
        cliente.setIdentificacion( clienteDTO.getIdentificacion() );
        cliente.setDireccion( clienteDTO.getDireccion() );
        cliente.setTelefono( clienteDTO.getTelefono() );
        cliente.setPassword( clienteDTO.getPassword() );
        cliente.setEstado( clienteDTO.getEstado() );

        return cliente;
    }

    protected Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO) {
        if ( cuentaDTO == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setId( cuentaDTO.getId() );
        cuenta.setCliente( clienteDTOToCliente( cuentaDTO.getCliente() ) );
        cuenta.setNumeroCuenta( cuentaDTO.getNumeroCuenta() );
        cuenta.setTipoCuenta( cuentaDTO.getTipoCuenta() );
        if ( cuentaDTO.getSaldoInicial() != null ) {
            cuenta.setSaldoInicial( cuentaDTO.getSaldoInicial() );
        }
        cuenta.setEstado( cuentaDTO.getEstado() );

        return cuenta;
    }

    protected ClienteDTO clienteToClienteDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setClienteId( cliente.getClienteId() );
        clienteDTO.setNombre( cliente.getNombre() );
        clienteDTO.setGenero( cliente.getGenero() );
        clienteDTO.setEdad( cliente.getEdad() );
        clienteDTO.setIdentificacion( cliente.getIdentificacion() );
        clienteDTO.setDireccion( cliente.getDireccion() );
        clienteDTO.setTelefono( cliente.getTelefono() );
        clienteDTO.setEstado( cliente.getEstado() );
        clienteDTO.setPassword( cliente.getPassword() );

        return clienteDTO;
    }

    protected CuentaDTO cuentaToCuentaDTO(Cuenta cuenta) {
        if ( cuenta == null ) {
            return null;
        }

        CuentaDTO cuentaDTO = new CuentaDTO();

        cuentaDTO.setId( cuenta.getId() );
        cuentaDTO.setNumeroCuenta( cuenta.getNumeroCuenta() );
        cuentaDTO.setTipoCuenta( cuenta.getTipoCuenta() );
        cuentaDTO.setSaldoInicial( cuenta.getSaldoInicial() );
        cuentaDTO.setEstado( cuenta.getEstado() );
        cuentaDTO.setCliente( clienteToClienteDTO( cuenta.getCliente() ) );

        return cuentaDTO;
    }
}
