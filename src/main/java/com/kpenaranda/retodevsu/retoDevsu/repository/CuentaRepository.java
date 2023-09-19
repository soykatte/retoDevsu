package com.kpenaranda.retodevsu.retoDevsu.repository;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cuenta;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

  Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
