package com.examen.jacome.mapper;

import com.examen.jacome.model.TurnoCaja;
import com.examen.jacome.enums.EstadoTurno;
import com.examen.jacome.dto.TurnoCajaDTO;
import org.springframework.stereotype.Component;

@Component
public class TurnoCajaMapper {

    public TurnoCajaDTO toDTO(TurnoCaja model) {
        if (model == null) {
            return null;
        }

        TurnoCajaDTO dto = new TurnoCajaDTO();
        dto.setCodigoCaja(model.getCodigoCaja());
        dto.setCodigoCajero(model.getCodigoCajero());
        dto.setCodigoTurno(model.getCodigoTurno());
        dto.setInicioTurno(model.getInicioTurno());
        dto.setMontoInicial(model.getMontoInicial());
        dto.setFinTurno(model.getFinTurno());
        dto.setMontoFinal(model.getMontoFinal());
        dto.setEstado(model.getEstado().toString()); // Convertimos el enum a String

        return dto;
    }

    public TurnoCaja toModel(TurnoCajaDTO dto) {
        if (dto == null) {
            return null;
        }

        TurnoCaja model = new TurnoCaja();
        model.setCodigoCaja(dto.getCodigoCaja());
        model.setCodigoCajero(dto.getCodigoCajero());
        model.setCodigoTurno(dto.getCodigoTurno());
        model.setInicioTurno(dto.getInicioTurno());
        model.setMontoInicial(dto.getMontoInicial());
        model.setFinTurno(dto.getFinTurno());
        model.setEstado(EstadoTurno.valueOf(dto.getEstado())); // Convertimos el String a enum

        return model;
    }
}

