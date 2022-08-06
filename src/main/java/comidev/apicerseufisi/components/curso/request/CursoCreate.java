package comidev.apicerseufisi.components.curso.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CursoCreate {
    @NotEmpty(message = "No puede ser vacio")
    private String nombre;
    @NotEmpty(message = "No puede ser vacio")
    private String codigo;
    @Positive(message = "Debe ser mayor a 1")
    private Integer ciclo;
    @Positive(message = "Debe ser mayor a 1")
    private Integer planDeEstudio;
    @Positive(message = "Debe ser mayor a 1")
    private Integer creditos;
}
