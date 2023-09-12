package sg.edu.nus.iss.movie_maven_backend.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.nus.iss.movie_maven_backend.exceptions.AppException;
import sg.edu.nus.iss.movie_maven_backend.records.ErrorDto;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = { AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                            .body(new ErrorDto(ex.getMessage()));
    }
}
