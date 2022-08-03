package comidev.apicerseufisi.components.notificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepo extends JpaRepository<Notificacion, Long> {

}
