package com.examen.jacome.repository;

import com.examen.jacome.model.TransaccionTurno;
import com.examen.jacome.enums.TipoTransaccion; // Asegúrate de importar desde enums

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.math.BigDecimal;

public interface TransaccionTurnoRepository extends MongoRepository<TransaccionTurno, String> {

    List<TransaccionTurno> findByCodigoTurno(String codigoTurno);

    List<TransaccionTurno> findByCodigoCaja(String codigoCaja);

    List<TransaccionTurno> findByCodigoCajero(String codigoCajero);

    List<TransaccionTurno> findByTipoTransaccion(TipoTransaccion tipoTransaccion); // Asegúrate de que esto esté correcto

    boolean existsByCodigoTurnoAndTipoTransaccion(String codigoTurno, TipoTransaccion tipoTransaccion);

    List<TransaccionTurno> findByCodigoTurnoAndTipoTransaccion(String codigoTurno, TipoTransaccion tipoTransaccion);

    // Método para obtener las transacciones dentro de un rango de fechas (opcional)
    List<TransaccionTurno> findByMontoTotalGreaterThanEqual(BigDecimal montoMin);
}
