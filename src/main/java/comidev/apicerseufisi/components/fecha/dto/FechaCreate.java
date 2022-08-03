package comidev.apicerseufisi.components.fecha.dto;

import javax.validation.constraints.NotEmpty;

import comidev.apicerseufisi.utils.Dia;
import lombok.Getter;

@Getter
public class FechaCreate {
    @NotEmpty(message = "No puede ser vacio")
    private Dia dia;

    @NotEmpty(message = "No puede ser vacio")
    private String inicio;

    @NotEmpty(message = "No puede ser vacio")
    private String fin;
}
