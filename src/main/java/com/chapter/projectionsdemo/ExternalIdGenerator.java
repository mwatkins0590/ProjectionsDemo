package com.chapter.projectionsdemo;


import java.util.UUID;
import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Component;

@Component
public class ExternalIdGenerator {

  public UUID generate() {
    return UuidCreator.getTimeOrderedEpoch();
  }
}
