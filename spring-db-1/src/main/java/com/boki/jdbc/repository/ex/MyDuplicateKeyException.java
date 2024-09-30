package com.boki.jdbc.repository.ex;

public class MyDuplicateKeyException extends MyDbException {
    public MyDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicateKeyException() {
    }

    public MyDuplicateKeyException(String message) {
        super(message);
    }

    public MyDuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
