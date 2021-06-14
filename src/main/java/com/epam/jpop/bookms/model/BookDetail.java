package com.epam.jpop.bookms.model;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookDetail {
    private Long id;

    @NotBlank(message = "Book name should not be null or empty")
    private String name;

    @NotBlank(message = "Book category should not be null or empty")
    private String category;

    @NotBlank(message = "Book author should not be null or empty")
    private String author;

    @NotBlank(message = "Book isbn should not be null or empty")
    private String isbn;

    private String Description;
}
