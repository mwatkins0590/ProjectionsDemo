package com.chapter.projectionsdemo;

import com.chapter.projectionsdemo.entities.Author;
import com.chapter.projectionsdemo.entities.Book;
import com.chapter.projectionsdemo.repositories.AuthorRepository;
import com.chapter.projectionsdemo.repositories.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

class TimingTest extends IntegrationTestBase {

  private static final int BOOKS_PER_AUTHOR = 100;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private TransactionDestroyerService transactionDestroyerService;

  @Autowired
  BookRepository bookRepository;

  List<UUID> authorIds = new ArrayList<>();

  @ParameterizedTest
  @CsvSource({
      "12345, 40",
      "12345, 500",
      "123456, 40",
      "123456, 500",
      "1234567, 40",
      "1234567, 500",
      "12345678, 40",
      "12345678, 500",
  })
  void runExperiment(int authorSeedSize, int numberOfReadSamples) {
    seedAuthors(authorSeedSize);
    seedBooks(BOOKS_PER_AUTHOR);
    doExperiment(setupBookTestCases(), authorSeedSize, numberOfReadSamples);
  }

  private void doExperiment(List<TestCase> testCases, int authorSeedSize, int numberOfReadSamples) {
    List<TestCaseResult> testCaseResults = new ArrayList<>();
    for (int i = 0; i < numberOfReadSamples; i++) {
      testCaseResults.addAll(doExperimentIteration(testCases));
    }

    Map<String, List<TestCaseResult>> groupedMap = testCaseResults.stream()
        .collect(Collectors.groupingBy(TestCaseResult::caseName));

    List<TestCaseReport> reports = new ArrayList<>();
    groupedMap.forEach((caseName, results) ->
        reports.add(TestCaseReport.calculate(results, authorSeedSize)));

    reports.forEach(System.out::println);

  }

  private List<TestCaseResult> doExperimentIteration(List<TestCase> testCases) {
    return shiftTestCases(testCases).stream().map(TestCaseResult::runTest).toList();
  }

  private List<TestCase> setupBookTestCases() {
    List<TestCase> testCases = new ArrayList<>();
    testCases.add(new TestCase("Entity", () -> bookRepository.findAll()));
    testCases.add(
        new TestCase("No Cache Entity", () -> transactionDestroyerService.doFreshEntityBookRead()));
    testCases.add(new TestCase("DTO Projection", () -> bookRepository.bookDTOProjectionRead()));
    testCases.add(
        new TestCase("Interface Projection", () -> bookRepository.bookInterfaceProjectionRead()));
    testCases.add(new TestCase("DTO Projection From Entity",
        () -> bookRepository.bookDTOProjectionReadFromEntity()));
    return testCases;
  }


  private List<TestCase> shiftTestCases(List<TestCase> testCases) {
    List<TestCase> shiftedCases = new ArrayList<>(testCases);
    TestCase firstCase = shiftedCases.removeFirst();
    shiftedCases.add(firstCase);
    return shiftedCases;
  }

  void seedAuthors(int numTotal) {
    List<Author> batch = new ArrayList<>();
    for (int i = 0; i < numTotal; i++) {
      batch.add(new Author(null, "Author" + i));

      if (batch.size() == 1000) {
        authorRepository.saveAll(batch);
        batch.clear();
      }
    }

    if (!batch.isEmpty()) {
      List<Author> saved = authorRepository.saveAll(batch);
      saved.stream().map(Author::getId).forEach(authorIds::add);
    }
  }

  void seedBooks(int booksPerAuthor) {
    List<Book> batch = new ArrayList<>();
    for (UUID authorId : authorIds) {
      for (int i = 0; i < booksPerAuthor; i++) {
        batch.add(Book.builder().isbn("ISBN-" + i)
            .title("Title " + i)
            .numberOfPages(100 + i)
            .printedAuthors("Mr. Pseudonym " + i)
            .build());

        if (batch.size() == 1000) {
          bookRepository.saveAll(batch);
          batch.clear();
          System.out.print(".");
        }
      }
    }
    if (!batch.isEmpty()) {
      bookRepository.saveAll(batch);
      System.out.println();
    }
  }
}
