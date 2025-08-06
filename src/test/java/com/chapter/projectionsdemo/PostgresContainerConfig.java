package com.chapter.projectionsdemo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class PostgresContainerConfig {

  @Getter
  @Setter
  private String test;

  @Bean
  @ServiceConnection
  @SuppressWarnings("resource")
  public PostgreSQLContainer<?> postgresContainer() {
    TestcontainersConfiguration.getInstance().updateUserConfig("testcontainers.reuse.enable", "true");
    PostgreSQLContainer<?> container = new PostgreSQLContainer<>(
        DockerImageName.parse("public.ecr.aws/docker/library/postgres:15.8")
            .asCompatibleSubstituteFor("postgres:15.8"))
        .withReuse(true)
        .withDatabaseName("library")
        .withUsername("postgres")
        .withPassword("postgres")
        .withExposedPorts(5432)
        .waitingFor(Wait.forListeningPorts(5432));
    makeContainerStopOnShutdown(container);

    container.start();

    return container;
  }

  private void makeContainerStopOnShutdown(PostgreSQLContainer<?> container) {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Stopping Postgres container...");
      container.stop();
    }));
  }

  @Bean
  @SneakyThrows
  public PostgresResetter postgresResetter(DataSource datasource) {
    return () -> truncateAllTables(datasource);
  }

  @SneakyThrows
  public void truncateAllTables(DataSource dataSource) {
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement()) {

      stmt.execute("SET session_replication_role = replica;");

      ResultSet rs = stmt.executeQuery("SELECT tablename FROM pg_tables WHERE schemaname = 'public';");

      List<String> tables = new ArrayList<>();
      while (rs.next()) {
        String tableName = rs.getString("tablename");
        if (!isLiquibaseTable(tableName)) {
          tables.add(tableName);
        }
      }

      for (String table : tables) {
        stmt.execute("TRUNCATE TABLE public." + table + " CASCADE;");
      }

      stmt.execute("SET session_replication_role = DEFAULT;");
    }
  }

  private boolean isLiquibaseTable(String tableName) {
    return tableName.equalsIgnoreCase("databasechangelog") || tableName.equalsIgnoreCase("databasechangeloglock");
  }
}