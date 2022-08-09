package comidev.apicerseufisi.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ValidatorTest {
    @Test
    void canCheckValidTime() {
        // Arreglar
        String text = "13:45";

        // Actuar
        LocalTime res = Validator.checkValidTime(text);

        // Afirmar
        assertEquals(res, LocalTime.of(13, 45));
    }

    @Test
    void cantCheckValidTime() {
        // Arreglar
        String text = "aa:aa";

        // Actuar
        HttpException res = assertThrows(HttpException.class, () -> {
            Validator.checkValidTime(text);
        });

        // Afirmar
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertTrue(res.getMessage().startsWith("Mal formato -> "));
    }

    @Test
    void canCheckValidDate() {
        // Arreglar
        String text = "2022-08-03";

        // Actuar
        LocalDate res = Validator.checkValidDate(text);

        // Afirmar
        assertEquals(res, LocalDate.of(2022, 8, 3));
    }

    @Test
    void cantCheckValidDate() {
        // Arreglar
        String text = "aaaa-mm-dd";

        // Actuar
        HttpException res = assertThrows(HttpException.class, () -> {
            Validator.checkValidDate(text);
        });

        // Afirmar
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertTrue(res.getMessage().startsWith("Mal formato -> "));
    }

    @Test
    void canCheckValidDateTime() {
        // Arreglar
        String text = "2022-08-03 13:45";

        // Actuar
        LocalDateTime res = Validator.checkValidDateTime(text);

        // Afirmar
        assertEquals(res, LocalDateTime.of(2022, 8, 3,
                13, 45));
    }

    @Test
    void cantCheckValidDateTime() {
        // Arreglar
        String text = "aaaa-MM-dd HH:mm";

        // Actuar
        HttpException res = assertThrows(HttpException.class, () -> {
            Validator.checkValidDateTime(text);
        });

        // Afirmar
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertTrue(res.getMessage().startsWith("Mal formato -> "));
    }
}
