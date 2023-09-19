package com.kpenaranda.retodevsu.retoDevsu.entity;

import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoMovimiento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimientos")
public class Movimiento implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "movimiento_id")
  private Long id;

  @NotNull(message = "La fecha no puede ser nula")
  private LocalDate fecha;

  @NotNull(message = "El tipo de movimiento no puede ser nulo")
  @Enumerated(EnumType.STRING)
  private TipoMovimiento tipoMovimiento;

  @NotNull(message = "El valor no puede ser nulo")
  private Double valor;

  @NotNull(message = "El saldo no puede ser nulo")
  private Double saldo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "numero_de_cuenta")
  private Cuenta cuenta;

}
