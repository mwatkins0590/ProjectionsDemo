package com.chapter.projectionsdemo.repositories;

import com.chapter.projectionsdemo.entities.Author;
import com.chapter.projectionsdemo.projections.AuthorDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, UUID> {


  @Query(value = "SELECT :id, :name FROM Author")
  List<AuthorDTO> dtoProjection();

//  Author save(Author author);

//  List<Author> saveAll(Iterable<Author> authors);
}
