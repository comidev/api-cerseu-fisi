package comidev.apicerseufisi.components.disponibilidad.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import comidev.apicerseufisi.components.fecha.dto.FechaCreate;
import lombok.Getter;

@Getter
public class DisponibilidadCreate {
    @NotEmpty(message = "No puede ser vacio")
    @Positive(message = "Debe ser mayor a 0")
    private Long docenteId;

    @NotEmpty(message = "No puede ser vacio")
    @Positive(message = "Debe ser mayor a 0")
    private Long cursoId;

    private List<@Valid FechaCreate> fechas;
}
