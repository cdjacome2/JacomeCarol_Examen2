package com.examen.jacome.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "DTO para la gestión de Turnos Caja")
public class TurnoCajaDTO {

    @Schema(description = "Código de la caja", example = "CAJ01")
    @NotBlank(message = "El código de la caja es requerido")
    private String codigoCaja;

    @Schema(description = "Código del cajero", example = "USU01")
    @NotBlank(message = "El código del cajero es requerido")
    private String codigoCajero;

    @Schema(description = "Código único del turno", example = "CAJ01-USU01-20250709")
    @NotBlank(message = "El código del turno es requerido")
    private String codigoTurno;

    @Schema(description = "Fecha y hora de inicio del turno", example = "2023-07-09T08:00:00")
    @NotNull(message = "La fecha de inicio del turno es requerida")
    private LocalDateTime inicioTurno;

    @Schema(description = "Monto inicial recibido al inicio del turno", example = "10000.00")
    @NotNull(message = "El monto inicial es requerido")
    @DecimalMin(value = "0.0", message = "El monto inicial no puede ser negativo")
    private BigDecimal montoInicial;

    @Schema(description = "Fecha y hora de cierre del turno", example = "2023-07-09T16:00:00")
    private LocalDateTime finTurno;

    @Schema(description = "Monto final al cierre del turno", example = "12000.00")
    private BigDecimal montoFinal;

    @Schema(description = "Estado del turno", example = "ABIERTO")
    @NotNull(message = "El estado es requerido")
    private String estado; // Puede ser ABIERTO o CERRADO
}

