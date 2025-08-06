package com.chapter.projectionsdemo.projections;

import com.chapter.projectionsdemo.entities.Book;
import java.util.UUID;

public record BookDTO(UUID id,
                      String isbn,
                      String title,
                      int numberOfPages,
                      String printedAuthors) {
  public BookDTO(Book b){
    this(
        b.getId(),
        b.getIsbn(),
        b.getTitle(),
        b.getNumberOfPages(),
        b.getPrintedAuthors()
    );
  }
}
