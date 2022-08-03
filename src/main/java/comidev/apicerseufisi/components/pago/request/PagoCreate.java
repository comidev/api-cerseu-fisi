package comidev.apicerseufisi.components.pago.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class PagoCreate {
    @Positive(message = "No puede ser menor a 1")
    private Float monto;
    @NotEmpty(message = "No puede ser vacio :D")
    private String cursoCodigo;
}
