package com.examen.jacome.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import com.examen.jacome.enums.TipoTransaccion;  // Importar desde el paquete enums

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "transacciones_turno")
public class TransaccionTurno {

    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;
    private TipoTransaccion tipoTransaccion;  // Utilizando el Enum desde enums
    private BigDecimal montoTotal;
    private List<Denominacion> denominaciones;

}
