package com.epam.jpop.bookms.service;

import com.epam.jpop.bookms.model.BookDetail;

import java.util.List;


public interface BookService {
    List<BookDetail> getAllBooks();

    BookDetail getBookById(Long id);

    BookDetail saveBook(BookDetail bookDetail);

    BookDetail updateBook(BookDetail bookDetail, Long id);

    void deleteBook(Long id);
}
