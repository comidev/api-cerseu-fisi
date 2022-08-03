package comidev.apicerseufisi.components.aula.request;

import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class AulaUpdate {
    @Positive(message = "No puede ser menor a 1")
    private Integer capacidad;
}
