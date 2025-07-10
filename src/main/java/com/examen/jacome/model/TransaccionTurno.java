package com.examen.jacome.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "transacciones_turno")
public class TransaccionTurno {

    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;
    private TipoTransaccion tipoTransaccion;
    private BigDecimal montoTotal;
    private List<Denominacion> denominaciones;

    public enum TipoTransaccion {
        INICIO,
        AHORRO,
        DEPOSITO,
        CIERRE
    }

    public static class Denominacion {
        private int billete;
        private int cantidadBilletes;
        private BigDecimal monto;
    }
}
