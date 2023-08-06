package com.springboot.exception;

public class LikesNotExistException extends RuntimeException{
    public LikesNotExistException(String message) {
        super(message);
    }
}
