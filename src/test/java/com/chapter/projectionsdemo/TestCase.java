package com.chapter.projectionsdemo;

public record TestCase(String name, Runnable command) {
  void run() {
    command.run();
  }

}
