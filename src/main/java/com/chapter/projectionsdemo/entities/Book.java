package com.chapter.projectionsdemo.entities;

import com.chapter.projectionsdemo.repositories.config.GeneratedUUIDV7;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

  @Id
  @GeneratedUUIDV7
  UUID id;
  String isbn;
  String title;
  int numberOfPages;
  String printedAuthors;

}
