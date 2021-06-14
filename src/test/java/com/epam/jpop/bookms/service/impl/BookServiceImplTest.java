package com.epam.jpop.bookms.service.impl;

import com.epam.jpop.bookms.entity.Book;
import com.epam.jpop.bookms.exception.BookAlreadyExists;
import com.epam.jpop.bookms.exception.BookNotFoundException;
import com.epam.jpop.bookms.mapper.BookMapper;
import com.epam.jpop.bookms.mapper.BookMapperImpl;
import com.epam.jpop.bookms.model.BookDetail;
import com.epam.jpop.bookms.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private final BookMapper bookMapper = new BookMapperImpl();
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(bookService, "bookMapper", bookMapper);
    }


    @Test
    void getAllBooks() {
        List<Book> books = prepareMockBookData();
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        List<BookDetail> bookDetails = bookService.getAllBooks();
        Assertions.assertNotNull(bookDetails);
        Assertions.assertFalse(bookDetails.isEmpty());
        Assertions.assertEquals(books.size(), bookDetails.size());
        Assertions.assertEquals(books.get(0).getId(), bookDetails.get(0).getId());
        Assertions.assertEquals(books.get(0).getAuthor(), bookDetails.get(0).getAuthor());
        Assertions.assertEquals(books.get(0).getName(), bookDetails.get(0).getName());
        Assertions.assertEquals(books.get(0).getCategory(), bookDetails.get(0).getCategory());
        Assertions.assertEquals(books.get(0).getDescription(), bookDetails.get(0).getDescription());
        Assertions.assertEquals(books.get(1).getId(), bookDetails.get(1).getId());
        Assertions.assertEquals(books.get(1).getAuthor(), bookDetails.get(1).getAuthor());
        Assertions.assertEquals(books.get(1).getName(), bookDetails.get(1).getName());
        Assertions.assertEquals(books.get(1).getCategory(), bookDetails.get(1).getCategory());
        Assertions.assertEquals(books.get(1).getDescription(), bookDetails.get(1).getDescription());
    }

    @Test
    void getBookById(){
        Book book = prepareMockBookData().get(0);
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
        BookDetail bookDetail = bookService.getBookById(1L);
        Assertions.assertNotNull(bookDetail);
        Assertions.assertEquals(book.getId(), bookDetail.getId());
        Assertions.assertEquals(book.getAuthor(), bookDetail.getAuthor());
        Assertions.assertEquals(book.getName(), bookDetail.getName());
        Assertions.assertEquals(book.getCategory(), bookDetail.getCategory());
        Assertions.assertEquals(book.getDescription(), bookDetail.getDescription());
    }

    @Test
    void saveBook(){
        BookDetail saveBookRequest = new BookDetail();
        saveBookRequest.setIsbn("MOCK ISBN");
        Book book = prepareMockBookData().get(0);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        Mockito.when(bookRepository.findByIsbn(Mockito.anyString())).thenReturn(Optional.empty());
        BookDetail savedBook = bookService.saveBook(saveBookRequest);
        Assertions.assertNotNull(savedBook);
        Assertions.assertEquals(book.getId(), savedBook.getId());
        Assertions.assertEquals(book.getAuthor(), savedBook.getAuthor());
        Assertions.assertEquals(book.getName(), savedBook.getName());
        Assertions.assertEquals(book.getCategory(), savedBook.getCategory());
        Assertions.assertEquals(book.getDescription(), savedBook.getDescription());

    }

    @Test
    void saveBookWithIsbnAlreadyAvailableInDb(){
        BookDetail saveBookRequest = new BookDetail();
        saveBookRequest.setIsbn("MOCK ISBN");
        Book book = prepareMockBookData().get(0);
        Mockito.when(bookRepository.findByIsbn(Mockito.anyString())).thenReturn(Optional.of(book));
        Assertions.assertThrows(BookAlreadyExists.class,()->bookService.saveBook(saveBookRequest));
        Mockito.verify(bookRepository,Mockito.times(0)).save(Mockito.any(Book.class));
    }

    @Test
    void updateBook(){
        BookDetail updateBookRequest = new BookDetail();
        Book book = prepareMockBookData().get(0);
        Mockito.when(bookRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        BookDetail updatedBookDetails  = bookService.updateBook(updateBookRequest,1L);
        Assertions.assertNotNull(updatedBookDetails);
        Assertions.assertEquals(book.getId(), updatedBookDetails.getId());
        Assertions.assertEquals(book.getAuthor(), updatedBookDetails.getAuthor());
        Assertions.assertEquals(book.getName(), updatedBookDetails.getName());
        Assertions.assertEquals(book.getCategory(), updatedBookDetails.getCategory());
        Assertions.assertEquals(book.getDescription(), updatedBookDetails.getDescription());
    }

    @Test
    void updateBookWithBookIdNotExistsInDb(){
        BookDetail updateBookRequest = new BookDetail();
        Book book = prepareMockBookData().get(0);
        Mockito.when(bookRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThrows(BookNotFoundException.class,()->bookService.updateBook(updateBookRequest,1L)) ;
        Mockito.verify(bookRepository,Mockito.times(0)).save(Mockito.any(Book.class));
    }

    @Test
    void deleteBook(){
        Mockito.when(bookRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.doNothing().when(bookRepository).deleteById(Mockito.anyLong());
        bookService.deleteBook(1L);
        Mockito.verify(bookRepository,Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void deleteBookWithBookNotExists(){
        Mockito.when(bookRepository.existsById(Mockito.anyLong())).thenReturn(false);
        bookService.deleteBook(1L);
        Mockito.verify(bookRepository,Mockito.times(0)).deleteById(Mockito.anyLong());
    }

    private List<Book> prepareMockBookData() {
        Book book1 = new Book(1L, "book1", "cat1", "a1", "desc1", "isbn111");
        Book book2 = new Book(2L, "book2", "cat2", "a2", "desc2", "isbn222");
        return List.of(book1, book2);
    }
}