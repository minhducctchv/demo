package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CustomException extends RuntimeException{

    private int status;
    private String message;

    public CustomException(int code, String message, Object ...args) {
        this.status = code;
        this.message = String.format(message, args);
    }

    public CustomException(String message, Object ...args) {
        this.status = 567;
        this.message = String.format(message, args);
    }
}
