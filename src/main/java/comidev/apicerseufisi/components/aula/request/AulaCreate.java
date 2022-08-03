package comidev.apicerseufisi.components.aula.request;

import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class AulaCreate {
    @Positive(message = "Debe ser mayor a 1")
    private Integer capacidad;
}
