package comidev.apicerseufisi.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Validator {
    private static final String TIME = "HH:mm";
    private static final String DATE = "aaaa-MM-dd";
    private static final String DATETIME = "aaaa-MM-dd HH:mm";

    private Validator() {
    }

    public static LocalTime checkValidTime(String text) {
        // Hora (hora:minuto) -> 13:14
        String message = formatError(text, TIME, "00<=HH<=23 y 00<=mm<=59");
        if (text.length() != TIME.length()
                || text.split(":").length != 2) {
            throw new HttpException(BAD_REQUEST, message);
        }
        try {
            return LocalTime.parse(text + ":00");
        } catch (Exception e) {
            throw new HttpException(BAD_REQUEST, message);
        }
    }

    public static LocalDate checkValidDate(String text) {
        // Fecha (año-mes-dia) -> 2022-08-03
        String message = formatError(text, DATE, "0000<=aaaa<=9999, 00<=MM<=12 y 00<=dd<=31");
        if (text.length() != DATE.length()
                || text.split("-").length != 3) {
            throw new HttpException(BAD_REQUEST, message);
        }
        try {
            return LocalDate.parse(text);
        } catch (Exception e) {
            throw new HttpException(BAD_REQUEST, message);
        }
    }

    public static LocalDateTime checkValidDateTime(String text) {
        // Fecha y hora (fecha hora) -> 2022-08-03 13:14
        String message = formatError(text, DATETIME,
                "0000<=aaaa<=9999, 00<=MM<=12, 00<=dd<=31, 00<=HH<=23 y 00<=mm<=59");
        if (text.length() != DATETIME.length()
                || text.split("-").length != 3
                || text.split(":").length != 2
                || text.split(" ").length != 2) {
            throw new HttpException(BAD_REQUEST, message);
        }
        try {
            // 2022-08-03T13:37:00
            text = text.replace(" ", "T") + ":00";
            return LocalDateTime.parse(text);
        } catch (Exception e) {
            throw new HttpException(BAD_REQUEST, message);
        }
    }

    private static String formatError(String text, String formato, String donde) {
        return "Mal formato -> '" + text + "' debe ser '" + formato + "', donde: " + donde;
    }
}
