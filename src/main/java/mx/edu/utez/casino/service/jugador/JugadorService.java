package mx.edu.utez.casino.service.jugador;

import jakarta.transaction.Transactional;
import mx.edu.utez.casino.controller.jugador.dto.RequestCreateJugadorDto;
import mx.edu.utez.casino.controller.jugador.dto.RequestRecargarSaldoDto;
import mx.edu.utez.casino.exception.customExceptions.CustomBadException;
import mx.edu.utez.casino.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.casino.model.jugador.Jugador;
import mx.edu.utez.casino.repository.jugador.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {
    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public List<Jugador> getAllJugadores() {
        return jugadorRepository.findAll();
    }

    public Jugador getJugadorById(Long id) {
        return buscarJugador(id);
    }

    @Transactional
    public Jugador createJugador(RequestCreateJugadorDto payload) {
        Jugador nuevoJugador = new Jugador();
        nuevoJugador.setNombre(payload.getNombre());
        nuevoJugador.setSaldo(payload.getSaldo());
        nuevoJugador.setActivo(true);
        return jugadorRepository.save(nuevoJugador);
    }

    @Transactional
    public Jugador recargarSaldo(Long id, RequestRecargarSaldoDto payload) {
        Jugador jugador = buscarJugador(id);

        jugador.setSaldo(jugador.getSaldo() + payload.getMonto());
        return jugadorRepository.save(jugador);
    }

    @Transactional
    public Jugador activarJugador(Long id) {
        Jugador jugador = buscarJugador(id);
        if (jugador.isActivo()) {
            throw new CustomBadException("El jugador ya esta activo");
        }
        jugador.setActivo(true);
        return jugadorRepository.save(jugador);
    }

    @Transactional
    public Jugador desactivarJugador(Long id) {
        Jugador jugador = buscarJugador(id);
        if (!jugador.isActivo()) {
            throw new CustomBadException("El jugador ya esta desactivado");
        }
        jugador.setActivo(false);
        return jugadorRepository.save(jugador);
    }

    private Jugador buscarJugador(Long id) {
        Optional<Jugador> jugadorExist = jugadorRepository.findById(id);
        if (jugadorExist.isEmpty()) {
            throw new CustomNotFoundException("El jugador no existe");
        }
        return jugadorExist.get();
    }
}
