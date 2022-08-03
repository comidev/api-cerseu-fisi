package comidev.apicerseufisi.components.pago;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import comidev.apicerseufisi.components.horario.Horario;
import comidev.apicerseufisi.components.pago.utils.PagoEstado;
import comidev.apicerseufisi.components.solicitud.Solicitud;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float monto;
    private String cursoCodigo;
    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;
    @ManyToOne
    @JoinColumn(name = "horario_id", nullable = true)
    private Horario horario;
    @Enumerated(EnumType.STRING)
    private PagoEstado pagoEstado;

    public Pago(Float monto, Solicitud solicitud) {
        this.monto = monto;
        this.solicitud = solicitud;
        this.pagoEstado = PagoEstado.CREADO;
    }
}
