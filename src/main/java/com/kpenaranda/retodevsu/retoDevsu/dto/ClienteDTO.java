package com.kpenaranda.retodevsu.retoDevsu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.Genero;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO {

  private Long clienteId;

  @NotEmpty(message = "El nombre es obligatorio")
  @Pattern(regexp = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$", message = "Nombre inválido")
  private String nombre;

  @Enumerated(EnumType.STRING)
  private Genero genero;

  @NotNull(message = "La edad es obligatoria")
  @Min(value = 1, message = "La edad mínima es 1")
  @Max(value = 120, message = "La edad máxima es 120")
  private Integer edad;

  @NotEmpty(message = "La identificación es obligatoria")
  @NotBlank(message = "La identificación no puede estar en blanco")
  @Pattern(regexp = "^[0-9]{6,13}$", message = "Identificación inválida")
  private String identificacion;

  @NotEmpty(message = "La dirección es obligatoria")
  @NotBlank(message = "La dirección no puede estar en blanco")
  @Pattern(regexp = "^[A-Za-z0-9\\s#-]+$", message = "Dirección inválida")
  private String direccion;

  @NotEmpty(message = "El teléfono es obligatorio")
  @NotBlank(message = "El teléfono no puede estar en blanco")
  @Pattern(regexp = "^[0-9]{6,13}$", message = "Teléfono inválido")
  private String telefono;

  private Boolean estado;

  @NotEmpty(message = "La contraseña es obligatoria")
  @NotBlank(message = "La contraseña no puede estar en blanco")
  private String password;
}
