package mx.edu.utez.casino.model.tiro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.casino.model.partida.Partida;

@Entity
@Table(name = "tiros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;

    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;
}
