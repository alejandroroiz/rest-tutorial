package com.base22.rest.tutorial.provider;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class LocalDateTimeProvider {

  public LocalDateTime now() {
    return LocalDateTime.now(ZoneOffset.UTC);
  }
}
