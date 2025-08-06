package com.chapter.projectionsdemo.repositories.config;

import com.github.f4b6a3.uuid.UuidCreator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UUIDV7Generator implements IdentifierGenerator {
  @Override
  public Object generate(SharedSessionContractImplementor session, Object object) {
    return UuidCreator.getTimeOrderedEpoch();
  }
}
