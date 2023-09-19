package com.kpenaranda.retodevsu.retoDevsu.entity;

import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.TipoCuenta;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas")
public class Cuenta implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @NotEmpty(message = "Número de cuenta es un campo obligatorio")
  @Pattern(regexp = "^[0-9]{6,11}$", message = "Número de cuenta inválido")
  @Column(name = "numero_de_cuenta", unique = true)
  private String numeroCuenta;

  @NotNull(message = "El tipo de cuenta no puede ser nulo")
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_de_cuenta")
  private TipoCuenta tipoCuenta;

  @NotNull(message = "El saldo inicial no puede ser nulo")
  @Column(name = "saldo_inicial")
  private double saldoInicial;

  @Column(name = "estado")
  private Boolean estado;

  @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Movimiento> movimientos;

}
