package org.aion4j.core.exception;

public class A4jProcessingException extends RuntimeException {

    public A4jProcessingException(String msg) {
        super(msg);
    }

    public A4jProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

}
