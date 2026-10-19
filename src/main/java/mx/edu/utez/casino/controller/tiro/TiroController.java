package mx.edu.utez.casino.controller.tiro;

import mx.edu.utez.casino.model.tiro.Tiro;
import mx.edu.utez.casino.service.tiro.TiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/tiro")
public class TiroController {
    private final TiroService tiroService;

    public TiroController(TiroService tiroService) {
        this.tiroService = tiroService;
    }

    @PostMapping("/partida/{partidaId}")
    public ResponseEntity<Tiro> realizarTiro(@PathVariable Long partidaId) {
        return ResponseEntity.status(201)
                .body(tiroService.realizarTiro(partidaId));
    }
}
