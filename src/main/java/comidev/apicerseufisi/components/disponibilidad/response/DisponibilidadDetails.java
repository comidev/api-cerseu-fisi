package comidev.apicerseufisi.components.disponibilidad.response;

import java.util.List;
import java.util.stream.Collectors;

import comidev.apicerseufisi.components.curso.response.CursoDetails;
import comidev.apicerseufisi.components.disponibilidad.Disponibilidad;
import comidev.apicerseufisi.components.fecha.dto.FechaResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DisponibilidadDetails {
    private Long id;
    private CursoDetails curso;
    private List<FechaResponse> fechas;

    public DisponibilidadDetails(Disponibilidad disponibilidad) {
        this.id = disponibilidad.getId();
        this.curso = new CursoDetails(disponibilidad.getCurso());
        this.fechas = disponibilidad.getFechas().stream()
                .map(FechaResponse::new)
                .collect(Collectors.toList());
    }
}
