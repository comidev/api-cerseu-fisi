package comidev.apicerseufisi.exceptions;

import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorMessage {
    private String error;
    private int status;
    private String message;
    private String method;
    private String path;
    private Timestamp timestamp;

    public ErrorMessage(HttpStatus status, String message, HttpServletRequest request) {
        this.error = status.getReasonPhrase().toUpperCase();
        this.status = status.value();
        this.message = message;
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.timestamp = Timestamp.from(Instant.now());
    }
}
