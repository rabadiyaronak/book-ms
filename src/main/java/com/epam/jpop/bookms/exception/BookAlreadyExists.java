package com.epam.jpop.bookms.exception;

//@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyExists extends RuntimeException {
    public BookAlreadyExists(String s) {
        super(s);
    }
}
