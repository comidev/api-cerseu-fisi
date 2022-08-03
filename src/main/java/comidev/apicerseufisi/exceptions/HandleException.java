package comidev.apicerseufisi.exceptions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class HandleException {
    // * Error del Cliente
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorMessage> generalError(HttpServletRequest request, HttpException exception) {
        ErrorMessage body = new ErrorMessage(
                exception.getStatus(),
                exception.getMessage(),
                request);
        return ResponseEntity.status(body.getStatus()).body(body);
    }

    // * Error del Cliente, Spring o Servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> unexpectedError(HttpServletRequest request, Exception exception) {
        ErrorMessage body = createMessage(request, exception);
        return ResponseEntity.status(body.getStatus()).body(body);
    }

    public static ErrorMessage createMessage(HttpServletRequest request, Exception exception) {
        String exceptionType = exception.getClass().getSimpleName();
        log.error("Tipo de Excepcion -> {}", exceptionType);

        HttpStatus status;
        String message = exception.getMessage();

        if (BAD_REQUEST.contains(exceptionType)) {
            status = HttpStatus.BAD_REQUEST;
            if ("MethodArgumentTypeMismatchException".equals(exceptionType)) {
                message = errorOfTypes(message);
            }
            if ("HttpMessageNotReadableException".equals(exceptionType)) {
                message = errorOfDesarialize(message);
            }
        } else if (UNAUTHORIZED.contains(exceptionType)) {
            status = HttpStatus.UNAUTHORIZED;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            exception.printStackTrace();
            log.error("Message -> {}", message);
        }
        return new ErrorMessage(status, message, request);
    }

    private static String errorOfTypes(String message) {
        if (message.contains("enum")) {
            String[] fields = message.split("\\.");
            int length = fields.length;
            message = "El (valor=" + fields[length - 1]
                    + ") no es un valor del enum " + fields[length - 2];
        } else if (message.contains("IllegalArgumentException: Invalid boolean")) {
            String[] fields = message.split(" ");
            String valor = fields[fields.length - 1];
            String simpleValor = valor.substring(
                    1, valor.length() - 1);
            message = "El (valor=" + simpleValor
                    + ") no es boolean (true o false).";
        } else if (message.contains("NumberFormatException")) {
            String[] fields = message.split(" ");
            String valor = fields[fields.length - 1];
            String simpleValor = valor.substring(
                    1, valor.length() - 1);
            if (message.contains("'int'")) {
                message = "El (valor=" + simpleValor
                        + ") no es un numero entero";
            } else {
                message = "El (valor=" + simpleValor
                        + ") no es un numero";
            }
        }
        return message;
    }

    private static String errorOfDesarialize(String message) {
        if (message.contains("Enum class")) {
            String messageTotal = message.split(";")[0];
            String[] fields1 = messageTotal.split(": ");
            int length = fields1.length;
            String valores = fields1[length - 1];
            String[] nameAndValue = messageTotal.split(" from String ");
            String value = nameAndValue[1].split(":")[0];
            value = value.substring(1, value.length() - 1);
            String[] fields3 = nameAndValue[0].split("\\.");
            int length3 = fields3.length;
            String name = fields3[length3 - 1];
            name = name.substring(0, name.length() - 1);
            return "El (valor=" + value + ") no es de tipo " + name
                    + ", este tipo tiene los siguientes valores: " + valores;
        }
        return message;
    }

    private static final List<String> BAD_REQUEST = List.of(
            "DuplicateKeyException",
            "HttpRequestMethodNotSupportedException",
            "MethodArgumentNotValidException",
            "MissingRequestHeaderException",
            "MissingServletRequestParameterException",
            "MethodArgumentTypeMismatchException",
            "HttpMessageNotReadableException");
    private static final List<String> UNAUTHORIZED = List.of(
            "AccessDeniedException",
            "InternalAuthenticationServiceException");
}
