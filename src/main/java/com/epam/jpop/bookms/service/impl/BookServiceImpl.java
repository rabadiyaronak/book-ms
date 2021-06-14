package com.epam.jpop.bookms.service.impl;

import com.epam.jpop.bookms.entity.Book;
import com.epam.jpop.bookms.exception.BookAlreadyExists;
import com.epam.jpop.bookms.exception.BookNotFoundException;
import com.epam.jpop.bookms.mapper.BookMapper;
import com.epam.jpop.bookms.model.BookDetail;
import com.epam.jpop.bookms.repository.BookRepository;
import com.epam.jpop.bookms.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public List<BookDetail> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toBookDetail(books);

    }

    @Override
    public BookDetail getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return bookMapper.toBookDetails(book.orElseThrow(() -> new BookNotFoundException("book not found with id -" + id)));
    }

    @Override
    public BookDetail saveBook(BookDetail bookDetail) {
        bookRepository.findByIsbn(bookDetail.getIsbn()).ifPresent(book -> {
            throw new BookAlreadyExists("book with " +
                    "given isbn - " + bookDetail.getIsbn() + " already exists");
        });

        var  book = bookMapper.toBook(bookDetail);
        var savedBook = bookRepository.save(book);
        return bookMapper.toBookDetails(savedBook);
    }

    @Override
    public BookDetail updateBook(BookDetail bookDetail, Long id) {
       if(!exists(id)){
           throw new BookNotFoundException("book not found with id - " + id);
       }
        bookDetail.setId(id);
        var bookToUpdate = bookMapper.toBook(bookDetail);
        var book  = bookRepository.save(bookToUpdate);

        return bookMapper.toBookDetails(book);
    }

    @Override
    public void deleteBook(Long id) {
        if(exists(id)){
            bookRepository.deleteById(id);
        }
    }

    private boolean exists(Long id) {
       return bookRepository.existsById(id);
    }


}
