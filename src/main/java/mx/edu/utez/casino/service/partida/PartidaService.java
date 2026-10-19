package mx.edu.utez.casino.service.partida;

import jakarta.transaction.Transactional;
import mx.edu.utez.casino.controller.partida.dto.RequestIniciarPartidaDto;
import mx.edu.utez.casino.exception.customExceptions.CustomBadException;
import mx.edu.utez.casino.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.casino.model.jugador.Jugador;
import mx.edu.utez.casino.model.partida.EstadoPartida;
import mx.edu.utez.casino.model.partida.Partida;
import mx.edu.utez.casino.repository.jugador.JugadorRepository;
import mx.edu.utez.casino.repository.partida.PartidaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PartidaService {
    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;

    public PartidaService(PartidaRepository partidaRepository, JugadorRepository jugadorRepository) {
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional
    public Partida iniciarPartida(RequestIniciarPartidaDto payload) {
        Optional<Jugador> jugadorExist = jugadorRepository.findById(payload.getJugadorId());
        if (jugadorExist.isEmpty()) {
            throw new CustomNotFoundException("El jugador no existe");
        }
        Jugador jugador = jugadorExist.get();

        if (!jugador.isActivo()) {
            throw new CustomBadException("El jugador no esta activo");
        }

        if (jugador.getSaldo() < payload.getApuesta()) {
            throw new CustomBadException("El jugador no tiene saldo suficiente");
        }

        jugador.setSaldo(jugador.getSaldo() - payload.getApuesta());
        jugadorRepository.save(jugador);

        Partida nuevaPartida = new Partida();
        nuevaPartida.setFecha(LocalDateTime.now());
        nuevaPartida.setEstado(EstadoPartida.EN_JUEGO);
        nuevaPartida.setJugador(jugador);
        nuevaPartida.setApuesta(payload.getApuesta());

        return partidaRepository.save(nuevaPartida);
    }

    @Transactional
    public Partida finalizarPartidaManual(Long id) {
        Optional<Partida> partidaExist = partidaRepository.findById(id);
        if (partidaExist.isEmpty()) {
            throw new CustomNotFoundException("La partida no existe");
        }
        Partida partida = partidaExist.get();

        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new CustomBadException("La partida ya esta finalizada");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepository.save(partida);
    }

    public List<Partida> obtenerHistorialPartida(Long jugadorId) {
        Optional<Jugador> jugadorExist = jugadorRepository.findById(jugadorId);
        if (jugadorExist.isEmpty()) {
            throw new CustomNotFoundException("El jugador no existe");
        }

        return partidaRepository.findByJugadorId(jugadorId);
    }
}
