package com.chapter.projectionsdemo.entities;

import com.chapter.projectionsdemo.repositories.config.GeneratedUUIDV7;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Author {

  @Id
  @GeneratedUUIDV7
  private UUID id;
  private String name;

}
