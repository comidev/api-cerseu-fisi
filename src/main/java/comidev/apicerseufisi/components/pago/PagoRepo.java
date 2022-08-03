package comidev.apicerseufisi.components.pago;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.apicerseufisi.components.pago.utils.PagoEstado;
import comidev.apicerseufisi.components.solicitud.Solicitud;

@Repository
public interface PagoRepo extends JpaRepository<Pago, Long> {
    List<Pago> findByCursoCodigoAndPagoEstado(String cursoCodigo, PagoEstado estado);

    List<Pago> findBySolicitudAndPagoEstado(Solicitud solicitud, PagoEstado estado);

    List<Pago> findByPagoEstado(PagoEstado estado);

    List<Pago> findBySolicitud(Solicitud solicitud);
}
