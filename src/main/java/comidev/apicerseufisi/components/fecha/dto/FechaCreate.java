package comidev.apicerseufisi.components.fecha.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import comidev.apicerseufisi.utils.Dia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FechaCreate {
    @NotNull(message = "No puede ser vacio")
    private Dia dia;

    @NotEmpty(message = "No puede ser vacio")
    private String inicio;

    @NotEmpty(message = "No puede ser vacio")
    private String fin;
}
