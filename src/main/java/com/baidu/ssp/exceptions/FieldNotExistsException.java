package com.baidu.ssp.exceptions;

/**
 * Created by mojie on 2015/2/10.
 */
public class FieldNotExistsException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public FieldNotExistsException(String message) {
        super(message);
    }
}
