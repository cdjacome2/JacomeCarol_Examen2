package com.examen.jacome.controller;

import com.examen.jacome.dto.TurnoCajaDTO;
import com.examen.jacome.dto.TransaccionTurnoDTO;
import com.examen.jacome.service.TurnoCajaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Turnos de Caja", description = "API para gestionar turnos de caja y transacciones.")
@RestController
@RequestMapping("/api/turnos-caja")
public class TurnoCajaController {

    private final TurnoCajaService turnoCajaService;

    @Autowired
    public TurnoCajaController(TurnoCajaService turnoCajaService) {
        this.turnoCajaService = turnoCajaService;
    }

    @Operation(summary = "Iniciar un nuevo turno de caja", 
               description = "Este endpoint permite iniciar un nuevo turno de caja.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Turno iniciado exitosamente"),
                   @ApiResponse(responseCode = "400", description = "Error al iniciar el turno")
               })
    @PostMapping("/iniciar")
    public ResponseEntity<TurnoCajaDTO> iniciarTurno(@RequestBody TurnoCajaDTO dto) {
        return ResponseEntity.ok(turnoCajaService.iniciarTurno(dto));
    }

    @Operation(summary = "Procesar una transacción en un turno de caja", 
               description = "Este endpoint permite procesar una transacción dentro de un turno.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Transacción procesada exitosamente"),
                   @ApiResponse(responseCode = "400", description = "Error al procesar la transacción")
               })
    @PostMapping("/transaccion")
    public ResponseEntity<TransaccionTurnoDTO> procesarTransaccion(@RequestBody TransaccionTurnoDTO dto) {
        return ResponseEntity.ok(turnoCajaService.procesarTransaccion(dto));
    }

    @Operation(summary = "Finalizar un turno de caja", 
               description = "Este endpoint permite finalizar un turno de caja, verificando que los montos coincidan.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Turno finalizado exitosamente"),
                   @ApiResponse(responseCode = "400", description = "Error al finalizar el turno")
               })
    @PutMapping("/finalizar/{codigoTurno}")
    public ResponseEntity<TurnoCajaDTO> finalizarTurno(@PathVariable String codigoTurno) {
        return ResponseEntity.ok(turnoCajaService.finalizarTurno(codigoTurno));
    }
}
