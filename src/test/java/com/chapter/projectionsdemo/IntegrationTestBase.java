package com.chapter.projectionsdemo;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostgresContainerConfig.class)
class IntegrationTestBase{

  @Autowired
  protected PostgresResetter postgresResetter;

  @AfterEach
  void resetDB() {
    postgresResetter.resetDb();
  }

}
