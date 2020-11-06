package com.base22.rest.tutorial.exception;

import org.apache.commons.configuration2.ex.ConfigurationException;

public class AppConfigException extends ConfigurationException {
  public AppConfigException(String message, Throwable cause) {
    super(message, cause);
  }
}
