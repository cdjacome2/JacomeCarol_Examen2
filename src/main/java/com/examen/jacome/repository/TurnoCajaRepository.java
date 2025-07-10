package com.examen.jacome.repository;

import com.examen.jacome.model.TurnoCaja;
import com.examen.jacome.enums.EstadoTurno;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface TurnoCajaRepository extends MongoRepository<TurnoCaja, String> {

    Optional<TurnoCaja> findByCodigoTurno(String codigoTurno);

    List<TurnoCaja> findByCodigoCajaAndEstado(String codigoCaja, EstadoTurno estado);

    List<TurnoCaja> findByCodigoCajeroAndEstado(String codigoCajero, EstadoTurno estado);

    boolean existsByCodigoTurno(String codigoTurno);

    boolean existsByCodigoCajaAndCodigoCajeroAndEstado(String codigoCaja, String codigoCajero, EstadoTurno estado);

    Optional<TurnoCaja> findByCodigoCajaAndCodigoCajeroAndEstado(String codigoCaja, String codigoCajero, EstadoTurno estado);

    // Método para encontrar turnos por fecha de inicio o cierre (opcional)
    List<TurnoCaja> findByInicioTurnoBetween(LocalDateTime inicio, LocalDateTime fin);
}
