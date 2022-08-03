package comidev.apicerseufisi.components.docente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepo extends JpaRepository<Docente, Long> {

}
