package com.examen.jacome.controller;

import com.examen.jacome.dto.TurnoCajaDTO;
import com.examen.jacome.dto.TransaccionTurnoDTO;
import com.examen.jacome.service.TurnoCajaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Turno Caja", description = "Operaciones relacionadas con la gestión de los turnos de caja y las transacciones")
@RestController
@RequestMapping("/api/turno-caja")
public class TurnoCajaController {

    private final TurnoCajaService turnoCajaService;

    public TurnoCajaController(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }

    @Operation(summary = "Iniciar un nuevo turno", description = "Crea un nuevo turno para el cajero y lo pone en estado ABIERTO")
    @PostMapping("/iniciar")
    public ResponseEntity<TurnoCajaDTO> iniciarTurno(@Valid @RequestBody TurnoCajaDTO dto) {
        return ResponseEntity.ok(turnoCajaService.iniciarTurno(dto));
    }

    @Operation(summary = "Procesar una transacción", description = "Registra una transacción (retiro o depósito) durante un turno")
    @PostMapping("/transaccion")
    public ResponseEntity<TransaccionTurnoDTO> procesarTransaccion(@Valid @RequestBody TransaccionTurnoDTO dto) {
        return ResponseEntity.ok(turnoCajaService.procesarTransaccion(dto));
    }

    @Operation(summary = "Finalizar un turno", description = "Cierra el turno y valida si el monto final coincide con las transacciones realizadas")
    @PutMapping("/finalizar/{codigoTurno}")
    public ResponseEntity<TurnoCajaDTO> finalizarTurno(@PathVariable String codigoTurno) {
        return ResponseEntity.ok(turnoCajaService.finalizarTurno(codigoTurno));
    }
}
