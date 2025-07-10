package com.examen.jacome.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "DTO para la gestión de Transacciones del Turno")
public class TransaccionTurnoDTO {

    @Schema(description = "Código de la caja", example = "CAJ01")
    @NotBlank(message = "El código de la caja es requerido")
    private String codigoCaja;

    @Schema(description = "Código del cajero", example = "USU01")
    @NotBlank(message = "El código del cajero es requerido")
    private String codigoCajero;

    @Schema(description = "Código único del turno", example = "CAJ01-USU01-20250709")
    @NotBlank(message = "El código del turno es requerido")
    private String codigoTurno;

    @Schema(description = "Tipo de transacción", example = "DEPOSITO")
    @NotBlank(message = "El tipo de transacción es requerido")
    private String tipoTransaccion; // Puede ser INICIO, AHORRO, DEPOSITO, CIERRE

    @Schema(description = "Monto total de la transacción", example = "2000.00")
    @NotNull(message = "El monto total es requerido")
    @DecimalMin(value = "0.0", message = "El monto total no puede ser negativo")
    private BigDecimal montoTotal;

    @Schema(description = "Denominaciones de billetes en la transacción")
    @NotNull(message = "Las denominaciones son requeridas")
    private List<DenominacionDTO> denominaciones;

    @Data
    @NoArgsConstructor
    public static class DenominacionDTO {
        @Schema(description = "Valor del billete", example = "100")
        @NotNull(message = "El valor del billete es requerido")
        private Integer billete; // Valor del billete (1, 5, 10, 20, 50, 100)

        @Schema(description = "Cantidad de billetes de esta denominación", example = "50")
        @NotNull(message = "La cantidad de billetes es requerida")
        private Integer cantidadBilletes;

        @Schema(description = "Monto total de esta denominación", example = "5000.00")
        @NotNull(message = "El monto es requerido")
        private BigDecimal monto;
    }
}

