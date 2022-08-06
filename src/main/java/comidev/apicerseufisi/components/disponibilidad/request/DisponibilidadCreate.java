package comidev.apicerseufisi.components.disponibilidad.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import comidev.apicerseufisi.components.fecha.dto.FechaCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadCreate {
    @NotNull(message = "No puede ser vacio")
    @Positive(message = "Debe ser mayor a 0")
    private Long docenteId;

    @NotNull(message = "No puede ser vacio")
    @Positive(message = "Debe ser mayor a 0")
    private Long cursoId;

    private List<@Valid FechaCreate> fechas;
}
