package comidev.apicerseufisi.components.pago.response;

import comidev.apicerseufisi.components.pago.Pago;
import comidev.apicerseufisi.components.pago.utils.PagoEstado;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PagoBySolicitud {
    private Long id;
    private Float monto;
    private String cursoCodigo;
    private PagoEstado pagoEstado;

    public PagoBySolicitud(Pago pago) {
        this.id = pago.getId();
        this.monto = pago.getMonto();
        this.cursoCodigo = pago.getCursoCodigo();
        this.pagoEstado = pago.getPagoEstado();
    }
}
