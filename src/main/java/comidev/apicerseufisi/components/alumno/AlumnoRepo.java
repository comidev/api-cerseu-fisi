package comidev.apicerseufisi.components.alumno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepo extends JpaRepository<Alumno, Long> {

}
