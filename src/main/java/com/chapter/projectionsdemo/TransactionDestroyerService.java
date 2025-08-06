package com.chapter.projectionsdemo;

import com.chapter.projectionsdemo.entities.Book;
import com.chapter.projectionsdemo.repositories.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionDestroyerService {

  private final BookRepository bookRepository;


  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public List<Book> doFreshEntityBookRead() {
    return bookRepository.findAll();
  }

}
