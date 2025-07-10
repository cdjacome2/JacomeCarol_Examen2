package com.examen.jacome.service;

import com.examen.jacome.dto.TurnoCajaDTO;
import com.examen.jacome.dto.TransaccionTurnoDTO;
import com.examen.jacome.exception.CreateEntityException;
import com.examen.jacome.exception.ResourceNotFoundException;
import com.examen.jacome.exception.UpdateEntityException;
import com.examen.jacome.mapper.TurnoCajaMapper;
import com.examen.jacome.mapper.TransaccionTurnoMapper;
import com.examen.jacome.model.TurnoCaja;
import com.examen.jacome.model.TransaccionTurno;
import com.examen.jacome.repository.TurnoCajaRepository;
import com.examen.jacome.repository.TransaccionTurnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoCajaService {

    private final TurnoCajaRepository turnoCajaRepository;
    private final TransaccionTurnoRepository transaccionTurnoRepository;
    private final TurnoCajaMapper turnoCajaMapper;
    private final TransaccionTurnoMapper transaccionTurnoMapper;

    public TurnoCajaService(TurnoCajaRepository turnoCajaRepository,
                            TransaccionTurnoRepository transaccionTurnoRepository,
                            TurnoCajaMapper turnoCajaMapper,
                            TransaccionTurnoMapper transaccionTurnoMapper) {
        this.turnoCajaRepository = turnoCajaRepository;
        this.transaccionTurnoRepository = transaccionTurnoRepository;
        this.turnoCajaMapper = turnoCajaMapper;
        this.transaccionTurnoMapper = transaccionTurnoMapper;
    }

    // --------- Métodos para Turno Caja ---------

    @Transactional
    public TurnoCajaDTO iniciarTurno(TurnoCajaDTO dto) {
        try {
            if (turnoCajaRepository.existsByCodigoTurno(dto.getCodigoTurno())) {
                throw new CreateEntityException("TurnoCaja", "Ya existe un turno con el mismo código: " + dto.getCodigoTurno());
            }

            TurnoCaja turnoCaja = turnoCajaMapper.toModel(dto);
            turnoCaja.setEstado(TurnoCaja.EstadoTurno.ABIERTO);
            turnoCaja.setInicioTurno(LocalDateTime.now());

            TurnoCaja nuevoTurno = turnoCajaRepository.save(turnoCaja);

            return turnoCajaMapper.toDTO(nuevoTurno);
        } catch (Exception e) {
            throw new CreateEntityException("TurnoCaja", "Error al iniciar el turno: " + e.getMessage());
        }
    }

    @Transactional
    public TransaccionTurnoDTO procesarTransaccion(TransaccionTurnoDTO dto) {
        try {
            TurnoCaja turnoCaja = turnoCajaRepository.findByCodigoTurno(dto.getCodigoTurno())
                    .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con el código: " + dto.getCodigoTurno()));

            TransaccionTurno transaccion = transaccionTurnoMapper.toModel(dto);
            transaccion.setCodigoTurno(turnoCaja.getCodigoTurno());

            // Actualizamos el monto total en el turno
            turnoCaja.setMontoFinal(turnoCaja.getMontoFinal().add(dto.getMontoTotal()));
            turnoCajaRepository.save(turnoCaja);

            TransaccionTurno nuevaTransaccion = transaccionTurnoRepository.save(transaccion);
            return transaccionTurnoMapper.toDTO(nuevaTransaccion);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CreateEntityException("TransaccionTurno", "Error al procesar la transacción: " + e.getMessage());
        }
    }

    @Transactional
    public TurnoCajaDTO finalizarTurno(String codigoTurno) {
        try {
            TurnoCaja turnoCaja = turnoCajaRepository.findByCodigoTurno(codigoTurno)
                    .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con el código: " + codigoTurno));

            // Comprobamos si el monto final coincide con las transacciones
            BigDecimal montoCalculado = turnoCaja.getMontoInicial();

            List<TransaccionTurno> transacciones = transaccionTurnoRepository.findByCodigoTurno(codigoTurno);
            for (TransaccionTurno transaccion : transacciones) {
                montoCalculado = montoCalculado.add(transaccion.getMontoTotal());
            }

            if (montoCalculado.compareTo(turnoCaja.getMontoFinal()) != 0) {
                throw new UpdateEntityException("TurnoCaja", "El monto final no coincide con las transacciones.");
            }

            turnoCaja.setEstado(TurnoCaja.EstadoTurno.CERRADO);
            turnoCaja.setFinTurno(LocalDateTime.now());

            TurnoCaja turnoCerrado = turnoCajaRepository.save(turnoCaja);

            return turnoCajaMapper.toDTO(turnoCerrado);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (UpdateEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new UpdateEntityException("TurnoCaja", "Error al finalizar el turno: " + e.getMessage());
        }
    }
}

