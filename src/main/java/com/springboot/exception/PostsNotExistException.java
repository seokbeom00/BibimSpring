package com.springboot.exception;

public class PostsNotExistException extends RuntimeException{
    public PostsNotExistException(String message) {
        super(message);
    }
}
