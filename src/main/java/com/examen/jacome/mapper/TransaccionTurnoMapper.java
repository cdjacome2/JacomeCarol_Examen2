package com.examen.jacome.mapper;

import com.examen.jacome.model.TransaccionTurno;
import com.examen.jacome.dto.TransaccionTurnoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransaccionTurnoMapper {

    public TransaccionTurnoDTO toDTO(TransaccionTurno model) {
        if (model == null) {
            return null;
        }

        TransaccionTurnoDTO dto = new TransaccionTurnoDTO();
        dto.setCodigoCaja(model.getCodigoCaja());
        dto.setCodigoCajero(model.getCodigoCajero());
        dto.setCodigoTurno(model.getCodigoTurno());
        dto.setTipoTransaccion(model.getTipoTransaccion().toString()); // Convertimos el enum a String
        dto.setMontoTotal(model.getMontoTotal());

        // Convertimos las denominaciones de entidad a DTO
        List<TransaccionTurnoDTO.DenominacionDTO> denominacionDTOList = model.getDenominaciones().stream()
                .map(denominacion -> {
                    TransaccionTurnoDTO.DenominacionDTO denominacionDTO = new TransaccionTurnoDTO.DenominacionDTO();
                    denominacionDTO.setBillete(denominacion.getBillete());
                    denominacionDTO.setCantidadBilletes(denominacion.getCantidadBilletes());
                    denominacionDTO.setMonto(denominacion.getMonto());
                    return denominacionDTO;
                })
                .collect(Collectors.toList());

        dto.setDenominaciones(denominacionDTOList);

        return dto;
    }

    public TransaccionTurno toModel(TransaccionTurnoDTO dto) {
        if (dto == null) {
            return null;
        }

        TransaccionTurno model = new TransaccionTurno();
        model.setCodigoCaja(dto.getCodigoCaja());
        model.setCodigoCajero(dto.getCodigoCajero());
        model.setCodigoTurno(dto.getCodigoTurno());
        model.setTipoTransaccion(TransaccionTurno.TipoTransaccion.valueOf(dto.getTipoTransaccion())); // Convertimos el String a enum
        model.setMontoTotal(dto.getMontoTotal());

        // Convertimos las denominaciones de DTO a entidad
        List<TransaccionTurno.Denominacion> denominacionList = dto.getDenominaciones().stream()
                .map(denominacionDTO -> {
                    TransaccionTurno.Denominacion denominacion = new TransaccionTurno.Denominacion();
                    denominacion.setBillete(denominacionDTO.getBillete());
                    denominacion.setCantidadBilletes(denominacionDTO.getCantidadBilletes());
                    denominacion.setMonto(denominacionDTO.getMonto());
                    return denominacion;
                })
                .collect(Collectors.toList());

        model.setDenominaciones(denominacionList);

        return model;
    }
}
