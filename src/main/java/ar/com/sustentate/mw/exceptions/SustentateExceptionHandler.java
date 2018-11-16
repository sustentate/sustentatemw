package ar.com.sustentate.mw.exceptions;

import com.cloudant.client.org.lightcouch.NoDocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SustentateExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoDocumentException.class)
    public void documentNotFound(NoDocumentException exception) {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NoUpdatableFormatException.class)
    public void documentDoesntHaveUpdatableFormat(NoUpdatableFormatException exception) {
    }

}