package comidev.apicerseufisi.components.horario.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import comidev.apicerseufisi.utils.Dia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HorarioCreate {
    @Positive(message = "No puede ser menor a 1")
    private Long cursoId;
    @Positive(message = "No puede ser menor a 1")
    private Long docenteId;
    @NotEmpty(message = "No puede ser vacio")
    private String inicio;
    @NotEmpty(message = "No puede ser vacio")
    private String fin;
    @NotNull(message = "No puede ser vacio")
    private Dia dia;
}
