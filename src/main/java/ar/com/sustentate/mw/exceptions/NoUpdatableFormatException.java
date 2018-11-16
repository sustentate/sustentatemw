package ar.com.sustentate.mw.exceptions;

import com.cloudant.client.org.lightcouch.CouchDbException;

public class NoUpdatableFormatException extends CouchDbException {
    public NoUpdatableFormatException(String message) {
        super(message);
    }

    public NoUpdatableFormatException(Throwable cause) {
        super(cause);
    }

    public NoUpdatableFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUpdatableFormatException(String message, int statusCode) {
        super(message, statusCode);
    }

    public NoUpdatableFormatException(String message, Throwable cause, int statusCode) {
        super(message, cause, statusCode);
    }
}
