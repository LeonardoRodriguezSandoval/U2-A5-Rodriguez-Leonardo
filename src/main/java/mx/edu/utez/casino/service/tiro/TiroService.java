package mx.edu.utez.casino.service.tiro;

import jakarta.transaction.Transactional;
import mx.edu.utez.casino.exception.customExceptions.CustomBadException;
import mx.edu.utez.casino.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.casino.model.jugador.Jugador;
import mx.edu.utez.casino.model.partida.EstadoPartida;
import mx.edu.utez.casino.model.partida.Partida;
import mx.edu.utez.casino.model.tiro.Tiro;
import mx.edu.utez.casino.repository.jugador.JugadorRepository;
import mx.edu.utez.casino.repository.partida.PartidaRepository;
import mx.edu.utez.casino.repository.tiro.TiroRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class TiroService {
    private final TiroRepository tiroRepository;
    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;

    private final Random random = new Random();

    public TiroService(TiroRepository tiroRepository,
                       PartidaRepository partidaRepository,
                       JugadorRepository jugadorRepository) {
        this.tiroRepository = tiroRepository;
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional
    public Tiro realizarTiro(Long partidaId) {
        Optional<Partida> partidaExist = partidaRepository.findById(partidaId);
        if (partidaExist.isEmpty()) {
            throw new CustomNotFoundException("La partida no existe");
        }
        Partida partida = partidaExist.get();

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new CustomBadException("La partida no esta en juego");
        }

        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;

        int suma = dado1 + dado2;

        boolean esGanador = false;

        if (suma == 7 || suma == 11) {
            esGanador = true;
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);

            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
        }

        Tiro nuevoTiro = new Tiro();
        nuevoTiro.setPartida(partida);
        nuevoTiro.setValorDado1(dado1);
        nuevoTiro.setValorDado2(dado2);
        nuevoTiro.setSuma(suma);
        nuevoTiro.setEsGanador(esGanador);

        return tiroRepository.save(nuevoTiro);
    }
}
