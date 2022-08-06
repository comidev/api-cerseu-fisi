package comidev.apicerseufisi.components.aula.request;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AulaCreate {
    @Positive(message = "Debe ser mayor a 1")
    private Integer capacidad;
}
