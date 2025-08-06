package com.chapter.projectionsdemo;

import java.time.Instant;

public record TestCaseResult (String caseName, long time){

  public static TestCaseResult runTest(TestCase testCase) {
    Instant before = Instant.now();
    testCase.run();
    Instant after = Instant.now();
    long duration = after.toEpochMilli() - before.toEpochMilli();
    return new TestCaseResult(testCase.name(), duration);
  }
}
