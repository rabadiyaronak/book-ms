package com.epam.jpop.bookms.controller;

import com.epam.jpop.bookms.model.BookDetail;
import com.epam.jpop.bookms.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDetail> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDetail getById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody BookDetail bookDetail) {
        BookDetail savedBook = bookService.saveBook(bookDetail);
        return ResponseEntity.created(URI.create("/api/v1/books/" + savedBook.getId())).build();
    }

    @PutMapping("/{id}")
    public BookDetail update(@Valid @RequestBody BookDetail bookDetail, @PathVariable  Long id) {
        return bookService.updateBook(bookDetail,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable  Long id) {
        bookService.deleteBook(id);
    }
}
