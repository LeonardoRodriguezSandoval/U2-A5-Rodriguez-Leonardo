package mx.edu.utez.casino.exception;

import mx.edu.utez.casino.exception.customExceptions.CustomBadException;
import mx.edu.utez.casino.exception.customExceptions.CustomNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errores.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(CustomBadException.class)
    public ResponseEntity<Map<String, String>> manejarCustomBadRequest(
            CustomBadException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(
            CustomNotFoundException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());
        return ResponseEntity.status(404).body(errores);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonInvalido(
            HttpMessageNotReadableException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", "El cuerpo de la peticion no es valido");
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> manejarTipoIncorrecto(
            MethodArgumentTypeMismatchException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", "El valor de '" + ex.getName() + "' no es valido");
        return ResponseEntity.badRequest().body(errores);
    }
}
