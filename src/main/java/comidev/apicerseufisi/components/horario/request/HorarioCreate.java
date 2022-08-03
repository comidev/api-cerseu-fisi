package comidev.apicerseufisi.components.horario.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import comidev.apicerseufisi.utils.Dia;
import lombok.Getter;

@Getter
public class HorarioCreate {
    @Positive(message = "No puede ser menor a 1")
    private Long cursoId;
    @Positive(message = "No puede ser menor a 1")
    private Long docenteId;
    @NotEmpty(message = "No puede ser vacio")
    private String inicio;
    @NotEmpty(message = "No puede ser vacio")
    private String fin;
    @NotEmpty(message = "No puede ser vacio")
    private Dia dia;
}
