package comidev.apicerseufisi.components.aula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepo extends JpaRepository<Aula, Long> {

}
