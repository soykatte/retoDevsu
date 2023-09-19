package com.kpenaranda.retodevsu.retoDevsu.entity;

import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.Genero;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personas")
@Entity
public class Persona implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cliente_id")
  private Long clienteId;

  @NotEmpty(message = "El nombre no puede estar vacío")
  @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "Nombre inválido")
  private String nombre;

  @NotNull(message = "El género no puede ser nulo")
  @Enumerated(EnumType.STRING)
  private Genero genero;

  @NotNull(message = "La edad no puede ser nula")
  @Min(value = 0, message = "La edad debe ser mayor o igual a 0")
  @Max(value = 120, message = "La edad debe ser menor o igual a 120")
  private Integer edad;

  @NotEmpty(message = "La identificación no puede estar vacía")
  @Column(unique = true)
  @Pattern(regexp = "^[0-9]{6,13}$", message = "Número inválido")
  private String identificacion;

  @NotEmpty(message = "La dirección no puede estar vacía")
  @Pattern(regexp = "^[A-Za-z0-9\\s#-]+$", message = "Dirección inválida")
  private String direccion;

  @NotEmpty(message = "El teléfono no puede estar vacío")
  @Pattern(regexp = "^[0-9]{6,13}$", message = "Teléfono inválido")
  private String telefono;
}