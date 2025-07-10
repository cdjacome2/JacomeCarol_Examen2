package com.examen.jacome.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Denominacion {

    private int billete;
    private int cantidadBilletes;
    private BigDecimal monto;

}
