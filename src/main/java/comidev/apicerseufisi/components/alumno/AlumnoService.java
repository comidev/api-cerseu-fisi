package comidev.apicerseufisi.components.alumno;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.components.usuario.response.UsuarioDetails;
import comidev.apicerseufisi.exceptions.HttpException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlumnoService {
    private final AlumnoRepo alumnoRepo;

    public Alumno findById(Long id) {
        return alumnoRepo.findById(id).orElseThrow(() -> {
            String message = "El Alumno -> (id=" + id + ") no existe!";
            return new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }

    public List<Alumno> findAll() {
        return alumnoRepo.findAll();
    }

    public UsuarioDetails saveAlumno(Usuario personaDB) {
        Alumno alumnoDB = alumnoRepo.save(new Alumno(personaDB));
        return new UsuarioDetails(alumnoDB.getId(), personaDB);
    }
}
