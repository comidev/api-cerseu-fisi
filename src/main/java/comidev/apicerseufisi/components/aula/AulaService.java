package comidev.apicerseufisi.components.aula;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<AulaDetails> getAllAulas() {
        return aulaRepo.findAll().stream()
                .map(AulaDetails::new)
                .collect(Collectors.toList());
    }

    public AulaDetails getAulaById(Long id) {
        return new AulaDetails(this.findAulaById(id));
    }

    public AulaDetails saveAula(AulaCreate body) {
        Aula aulaNEW = new Aula(body);
        return new AulaDetails(aulaRepo.save(aulaNEW));
    }

    public void updateAula(Long id, AulaUpdate body) {
        Aula aulaDB = this.findAulaById(id);
        aulaDB.update(body);
        aulaRepo.save(aulaDB);
    }

    public void deleteAula(Long id) {
        aulaRepo.delete(this.findAulaById(id));
    }

    public Aula findAulaById(Long id) {
        return aulaRepo.findById(id).orElseThrow(() -> {
            String message = "El Aula con id -> (" + id + ") no existe!";
            return new HttpException(HttpStatus.NOT_FOUND, message);
        });
    }
}
