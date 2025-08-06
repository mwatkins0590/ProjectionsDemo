package com.chapter.projectionsdemo;

@FunctionalInterface
public interface PostgresResetter {
  void resetDb();
}