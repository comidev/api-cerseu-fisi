package comidev.apicerseufisi.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public HttpException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
