package com.chapter.projectionsdemo.repositories;

import com.chapter.projectionsdemo.entities.Book;
import com.chapter.projectionsdemo.projections.BookDTO;
import com.chapter.projectionsdemo.projections.BookInterfaceProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, UUID> {

  @Query("SELECT new com.chapter.projectionsdemo.projections.BookDTO(b.id, b.isbn, b.title, b.numberOfPages, b.printedAuthors) FROM Book b")
  List<BookDTO> bookDTOProjectionRead();

  @Query("SELECT b FROM Book b")
  List<BookInterfaceProjection> bookInterfaceProjectionRead();

  @Query("Select new com.chapter.projectionsdemo.projections.BookDTO(b) FROM Book b")
  List<BookDTO> bookDTOProjectionReadFromEntity();

}
