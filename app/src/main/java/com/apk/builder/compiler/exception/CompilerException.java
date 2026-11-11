package com.tyron.compiler.exception;

public class CompilerException extends Exception {
    
    public CompilerException(String message) {
        super(message);
    }
    
    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CompilerException(Throwable cause) {
        super(cause);
    }
}