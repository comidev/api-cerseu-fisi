package comidev.apicerseufisi.components.solicitud.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudCreate {
    @NotEmpty(message = "No puede ser vacio")
    private String codigo;
    @Positive(message = "No puede ser menor a 1")
    private Long alumnoId;
}
