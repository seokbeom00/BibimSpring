package com.springboot.exception;

public class MemberNotExistException extends RuntimeException{
    public MemberNotExistException(String message) {
        super(message);
    }
}
