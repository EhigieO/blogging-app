package com.bomen.blogging.exceptions;

public class BlogAppException extends Exception {
    public BlogAppException() {
        super();
    }

    public BlogAppException(String message) {
        super(message);
    }

    public BlogAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogAppException(Throwable cause) {
        super(cause);
    }
}
