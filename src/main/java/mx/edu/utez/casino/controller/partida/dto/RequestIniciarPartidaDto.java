package mx.edu.utez.casino.controller.partida.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestIniciarPartidaDto {
    @NotNull(message = "El id del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @Positive(message = "La apuesta debe ser mayor a 0")
    private Double apuesta;
}
