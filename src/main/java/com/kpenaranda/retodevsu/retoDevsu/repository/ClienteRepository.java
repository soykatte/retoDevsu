package com.kpenaranda.retodevsu.retoDevsu.repository;

import com.kpenaranda.retodevsu.retoDevsu.entity.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  Optional<Cliente> findByIdentificacion(String identificacion);
}
