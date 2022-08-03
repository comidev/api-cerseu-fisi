package comidev.apicerseufisi.components.docente;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.usuario.Usuario;
import comidev.apicerseufisi.components.usuario.response.UsuarioDetails;
import comidev.apicerseufisi.exceptions.HttpException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocenteService {
    private final DocenteRepo docenteRepo;

    public Docente findById(Long id) {
        return docenteRepo.findById(id).orElseThrow(() -> {
            String message = "El Docente -> (id=" + id + ") no existe!";
            return new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }

    public List<Docente> findAll() {
        return docenteRepo.findAll();
    }

    public UsuarioDetails saveDocente(Usuario usuarioDB) {
        Docente docenteDB = docenteRepo.save(new Docente(usuarioDB));
        return new UsuarioDetails(docenteDB.getId(), usuarioDB);
    }
}
