package comidev.apicerseufisi.components.aula;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import comidev.apicerseufisi.components.aula.request.AulaCreate;
import comidev.apicerseufisi.components.aula.request.AulaUpdate;
import comidev.apicerseufisi.components.aula.response.AulaDetails;
import comidev.apicerseufisi.exceptions.HttpException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AulaService {
    private final AulaRepo aulaRepo;

    public Aula findById(Long id) {
        return aulaRepo.findById(id).orElseThrow(() -> {
            String message = "El Alumno con id -> (" + id + ") no existe!";
            return new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }

    public List<AulaDetails> getAll() {
        return aulaRepo.findAll().stream()
                .map(AulaDetails::new).toList();
    }

    public AulaDetails getById(Long id) {
        return new AulaDetails(this.findById(id));
    }

    public AulaDetails saveAula(AulaCreate aulaCreate) {
        Aula aulaNEW = new Aula(aulaCreate);
        return new AulaDetails(aulaRepo.save(aulaNEW));
    }

    public void updateAula(AulaUpdate aulaUpdate, Long id) {
        Aula aulaDB = this.findById(id);
        aulaDB.update(aulaUpdate);
        aulaRepo.save(aulaDB);
    }

    public void eliminarAula(Long id) {
        aulaRepo.delete(this.findById(id));
    }
}
