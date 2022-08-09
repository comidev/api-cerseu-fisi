package comidev.apicerseufisi.components.disponibilidad.response;

import java.util.List;
import java.util.stream.Collectors;

import comidev.apicerseufisi.components.disponibilidad.Disponibilidad;
import comidev.apicerseufisi.components.fecha.dto.FechaResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DisponibilidadListAll {
    private Long id;
    private Long docenteId;
    private String cursoCodigo;
    private List<FechaResponse> fechas;

    public DisponibilidadListAll(Disponibilidad disponibilidad) {
        this.id = disponibilidad.getId();
        this.docenteId = disponibilidad.getDocente().getId();
        this.cursoCodigo = disponibilidad.getCurso().getCodigo();
        this.fechas = disponibilidad.getFechas().stream()
                .map(FechaResponse::new)
                .collect(Collectors.toList());
    }
}
