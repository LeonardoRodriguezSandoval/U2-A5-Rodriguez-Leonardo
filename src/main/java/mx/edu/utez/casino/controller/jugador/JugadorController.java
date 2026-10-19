package mx.edu.utez.casino.controller.jugador;

import jakarta.validation.Valid;
import mx.edu.utez.casino.controller.jugador.dto.RequestCreateJugadorDto;
import mx.edu.utez.casino.controller.jugador.dto.RequestRecargarSaldoDto;
import mx.edu.utez.casino.model.jugador.Jugador;
import mx.edu.utez.casino.service.jugador.JugadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/jugador")
public class JugadorController {
    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping
    public ResponseEntity<List<Jugador>> getAllJugadores() {
        return ResponseEntity.status(200)
                .body(jugadorService.getAllJugadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> getJugadorById(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(jugadorService.getJugadorById(id));
    }

    @PostMapping
    public ResponseEntity<Jugador> createJugador(@RequestBody @Valid RequestCreateJugadorDto payload) {
        return ResponseEntity.status(201)
                .body(jugadorService.createJugador(payload));
    }

    @PatchMapping("/{id}/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@PathVariable Long id,
                                                 @RequestBody @Valid RequestRecargarSaldoDto payload) {
        return ResponseEntity.status(200)
                .body(jugadorService.recargarSaldo(id, payload));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Jugador> activarJugador(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(jugadorService.activarJugador(id));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Jugador> desactivarJugador(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(jugadorService.desactivarJugador(id));
    }
}
