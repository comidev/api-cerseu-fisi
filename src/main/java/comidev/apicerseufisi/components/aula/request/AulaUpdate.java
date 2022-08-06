package comidev.apicerseufisi.components.aula.request;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AulaUpdate {
    @Positive(message = "No puede ser menor a 1")
    private Integer capacidad;
}
