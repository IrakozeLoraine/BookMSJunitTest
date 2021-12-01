package com.example.BookMSJunitTest.utils.Exceptions;

public class ResourceNotFoundException extends  RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue) {
        super(String.format("%s with %s ['%s'] " +
                "is not found", resourceName, fieldName, fieldValue));
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with %s ['%s'] " +
                "is not found", resourceName, fieldName, fieldValue));
    }

}
