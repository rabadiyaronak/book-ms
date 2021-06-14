package com.epam.jpop.bookms.mapper;

import com.epam.jpop.bookms.entity.Book;
import com.epam.jpop.bookms.model.BookDetail;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDetail toBookDetails(Book book);

    List<BookDetail> toBookDetail(List<Book> books);

    Book toBook(BookDetail bookDetail);

    List<Book> toBook(List<BookDetail> bookDetails);

}
