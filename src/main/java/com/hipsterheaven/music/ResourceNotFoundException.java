package com.hipsterheaven.music;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
