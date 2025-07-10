package com.examen.jacome.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.examen.jacome.enums.EstadoTurno;  // Importar desde el paquete enums

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "codigoTurno")
@Document(collection = "turnos_caja")
public class TurnoCaja {

    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;
    private LocalDateTime inicioTurno;
    private BigDecimal montoInicial;
    private LocalDateTime finTurno;
    private BigDecimal montoFinal;
    private EstadoTurno estado;  // Utilizando el Enum desde enums

}
