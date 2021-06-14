package com.epam.jpop.bookms.exception;


//@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {


    public BookNotFoundException(String message) {
        super(message);

    }
}
