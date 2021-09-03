package dev.weverton.com.blogapi.exceptions;

public class DuplicatedKeyException extends RuntimeException{
    public DuplicatedKeyException(String msg){
        super(msg);
    }
}
