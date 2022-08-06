package comidev.apicerseufisi.components.pago.request;

import java.util.List;

import javax.validation.Valid;

import comidev.apicerseufisi.components.solicitud.request.SolicitudCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PagosCreate {
    @Valid
    private SolicitudCreate solicitud;
    private List<@Valid PagoCreate> pagos;
}
