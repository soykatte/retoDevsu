package com.kpenaranda.retodevsu.retoDevsu.repository;

import com.kpenaranda.retodevsu.retoDevsu.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

}
