package com.example.exception;
public class BarcodeAlreadyExistsException extends RuntimeException {
    public BarcodeAlreadyExistsException(String message) { super(message); }
}
