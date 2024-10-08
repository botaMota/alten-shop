package com.alten.backend.exceptions;

import com.alten.backend.dto.Error;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.webjars.NotFoundException;

import java.sql.Timestamp;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionAdviser {
	
	Logger logger = LogManager.getLogger(ExceptionAdviser.class);


    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<Error> functionalException(final FunctionalException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.ok(getError("Functional exception", e.getMessage() , HttpStatus.FOUND.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> httpMessageNotReadableException(final HttpMessageNotReadableException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.ok(getError("Malformed JSON request", e.getMostSpecificCause().getMessage() , HttpStatus.BAD_REQUEST.value()));
    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> notFoundException(final NotFoundException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.ok(getError("Item not found", e.getMessage() , HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "Type mismatch for parameter '" + ex.getName() + "'. Expected type: " + ex.getRequiredType().getSimpleName();
        return ResponseEntity.ok(getError("Bad params type", message , HttpStatus.BAD_REQUEST.value()));
    }



    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> noSuchElementException(final NoSuchElementException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.ok(getError("Item not found", e.getMessage() , HttpStatus.BAD_REQUEST.value()));
    }


	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> runtimeException(final RuntimeException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.ok(getError("Internal server Problem","Internal server error:"+e.getMessage()+". Please check logs", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
	
	
	
	
	private Error getError(String message, String details, Integer code) {
        Error error = new Error();
        error.setMessage(message);
        error.setCode(code);
        error.setDetails(details);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        error.setTimeStamp(timestamp.toString());
        return error;
    }
}
