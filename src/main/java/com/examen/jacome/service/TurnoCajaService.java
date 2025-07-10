package com.examen.jacome.service;

import com.examen.jacome.dto.TurnoCajaDTO;
import com.examen.jacome.dto.TransaccionTurnoDTO;
import com.examen.jacome.exception.CreateEntityException;
import com.examen.jacome.exception.ResourceNotFoundException;
import com.examen.jacome.exception.UpdateEntityException;
import com.examen.jacome.enums.EstadoTurno;
import com.examen.jacome.mapper.TurnoCajaMapper;
import com.examen.jacome.mapper.TransaccionTurnoMapper;
import com.examen.jacome.model.TurnoCaja;
import com.examen.jacome.model.TransaccionTurno;
import com.examen.jacome.repository.TurnoCajaRepository;
import com.examen.jacome.repository.TransaccionTurnoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoCajaService {

    private static final Logger logger = LoggerFactory.getLogger(TurnoCajaService.class);

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
        logger.info("Intentando iniciar turno con código: {}", dto.getCodigoTurno());
        try {
            if (turnoCajaRepository.existsByCodigoTurno(dto.getCodigoTurno())) {
                logger.warn("El turno con el código {} ya existe", dto.getCodigoTurno());
                throw new CreateEntityException("TurnoCaja", "Ya existe un turno con el mismo código: " + dto.getCodigoTurno());
            }

            TurnoCaja turnoCaja = turnoCajaMapper.toModel(dto);
            turnoCaja.setEstado(EstadoTurno.ABIERTO);
            turnoCaja.setInicioTurno(LocalDateTime.now());

            TurnoCaja nuevoTurno = turnoCajaRepository.save(turnoCaja);

            logger.info("Turno con código {} iniciado exitosamente", turnoCaja.getCodigoTurno());
            return turnoCajaMapper.toDTO(nuevoTurno);
        } catch (Exception e) {
            logger.error("Error al iniciar el turno con código {}: {}", dto.getCodigoTurno(), e.getMessage());
            throw new CreateEntityException("TurnoCaja", "Error al iniciar el turno: " + e.getMessage());
        }
    }

    @Transactional
    public TransaccionTurnoDTO procesarTransaccion(TransaccionTurnoDTO dto) {
        logger.info("Procesando transacción para el turno con código: {}", dto.getCodigoTurno());
        try {
            TurnoCaja turnoCaja = turnoCajaRepository.findByCodigoTurno(dto.getCodigoTurno())
                    .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con el código: " + dto.getCodigoTurno()));

            TransaccionTurno transaccion = transaccionTurnoMapper.toModel(dto);
            transaccion.setCodigoTurno(turnoCaja.getCodigoTurno());

            // Actualizamos el monto total en el turno
            turnoCaja.setMontoFinal(turnoCaja.getMontoFinal().add(dto.getMontoTotal()));
            turnoCajaRepository.save(turnoCaja);

            TransaccionTurno nuevaTransaccion = transaccionTurnoRepository.save(transaccion);
            logger.info("Transacción procesada correctamente para el turno con código: {}", dto.getCodigoTurno());
            return transaccionTurnoMapper.toDTO(nuevaTransaccion);
        } catch (ResourceNotFoundException e) {
            logger.warn("Turno con código {} no encontrado: {}", dto.getCodigoTurno(), e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al procesar la transacción para el turno con código {}: {}", dto.getCodigoTurno(), e.getMessage());
            throw new CreateEntityException("TransaccionTurno", "Error al procesar la transacción: " + e.getMessage());
        }
    }

    @Transactional
    public TurnoCajaDTO finalizarTurno(String codigoTurno) {
        logger.info("Finalizando turno con código: {}", codigoTurno);
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
                logger.warn("El monto calculado ({}) no coincide con el monto final del turno ({})", montoCalculado, turnoCaja.getMontoFinal());
                throw new UpdateEntityException("TurnoCaja", "El monto final no coincide con las transacciones.");
            }

            turnoCaja.setEstado(EstadoTurno.CERRADO);
            turnoCaja.setFinTurno(LocalDateTime.now());

            TurnoCaja turnoCerrado = turnoCajaRepository.save(turnoCaja);
            logger.info("Turno con código {} cerrado exitosamente", codigoTurno);

            return turnoCajaMapper.toDTO(turnoCerrado);
        } catch (ResourceNotFoundException e) {
            logger.error("Error al finalizar el turno con código {}: {}", codigoTurno, e.getMessage());
            throw e;
        } catch (UpdateEntityException e) {
            logger.error("Error al actualizar el turno con código {}: {}", codigoTurno, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al finalizar el turno con código {}: {}", codigoTurno, e.getMessage());
            throw new UpdateEntityException("TurnoCaja", "Error al finalizar el turno: " + e.getMessage());
        }
    }
}
