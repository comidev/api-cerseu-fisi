package comidev.apicerseufisi.exceptions;

import java.time.LocalDateTime;

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
    private String timestamp;

    public ErrorMessage(HttpStatus status, String message, HttpServletRequest request) {
        this.error = status.getReasonPhrase().toUpperCase();
        this.status = status.value();
        this.message = message;
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.timestamp = LocalDateTime.now()
                .toString()
                .replace("T", " ");
    }
}
