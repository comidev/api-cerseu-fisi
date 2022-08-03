package comidev.apicerseufisi.components.fecha;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import comidev.apicerseufisi.components.fecha.dto.FechaCreate;
import comidev.apicerseufisi.exceptions.HttpException;
import comidev.apicerseufisi.exceptions.Validator;
import comidev.apicerseufisi.utils.Dia;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "fechas")
public class Fecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Dia dia;
    private LocalTime inicio;
    private LocalTime fin;

    public Fecha(FechaCreate fechaCreate) {
        this.dia = fechaCreate.getDia();
        this.inicio = Validator.checkValidTime(fechaCreate.getInicio());
        this.fin = Validator.checkValidTime(fechaCreate.getFin());
        validarHorario();
    }

    private void validarHorario() {
        if (inicio.getHour() == 0 || fin.getHour() == 0 || inicio.equals(fin)) {
            String message = "Inicio o fin no puede ser 00:00, tampoco pueden ser igual";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
        if (fin.isBefore(inicio)) {
            String message = "'fin' no puede ser antes de 'inicio'";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
        if (fin.isAfter(LocalTime.of(22, 0))) {
            String message = "El horario no debe ser mayor de 22:00";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
        if (inicio.isBefore(LocalTime.of(8, 0))) {
            String message = "El horario no debe ser menor de 08:00";
            throw new HttpException(HttpStatus.BAD_REQUEST, message);
        }
    }
}
