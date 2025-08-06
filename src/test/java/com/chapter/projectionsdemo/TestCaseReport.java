package com.chapter.projectionsdemo;

import java.util.List;

public record TestCaseReport(String testCase, double average, double median, long min, long max, long range, long rowCount, int readCount) {

  public static TestCaseReport calculate(List<TestCaseResult> resultSet, long rowCount) {
    int readCount = resultSet.size();
    List<Long> sortedTimes = resultSet.stream().map(TestCaseResult::time).sorted().toList();
    String testCase = resultSet.getFirst().caseName();

    long minimum = sortedTimes.getFirst();
    long maximum = sortedTimes.getLast();
    double median = calcMedian(sortedTimes);
    long total = sortedTimes.stream().mapToLong(Long::longValue).sum();

    double average = total / (double) sortedTimes.size();
    long range = maximum - minimum;
    return new TestCaseReport(testCase, average, median, minimum, maximum, range, rowCount, readCount);
  }


  private static double calcMedian(List<Long> sorted) {
    double median;
    int size = sorted.size();
    if (size % 2 == 0) {
      median = (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
    } else {
      median = sorted.get(size / 2);
    }
    return  median;
  }

  private String statFormat(double value) {
    return String.format("%.2f", value);
  }


  @Override
  public String toString() {
    return "{\n" +
        "case: " + testCase() + "\n" +
        "average: " + statFormat(average()) + "\n" +
        "median: " + statFormat(median()) + "\n" +
        "min: " + min() + "\n" +
        "max: " + max() + "\n" +
        "range: " + range()+"\n" +
        "row count: " + rowCount() +"\n" +
        "read count: " + readCount() +"\n}\n";
  }

}
