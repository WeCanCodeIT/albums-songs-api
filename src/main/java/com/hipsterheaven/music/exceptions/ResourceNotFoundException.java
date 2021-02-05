package com.hipsterheaven.music.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
