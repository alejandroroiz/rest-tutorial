package com.base22.rest.tutorial.exception;

import com.base22.rest.tutorial.domain.model.jpa.CustomerNotFoundException;
import com.base22.rest.tutorial.domain.model.jpa.EmailNotValidException;
import com.base22.rest.tutorial.domain.model.jpa.UsernameNotValidException;
import com.base22.rest.tutorial.provider.LocalDateTimeProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.NamingException;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LogManager.getLogger();
  private final LocalDateTimeProvider localDateTimeProvider;

  public GlobalExceptionHandler(LocalDateTimeProvider localDateTimeProvider) {
    this.localDateTimeProvider = localDateTimeProvider;
  }

  @ExceptionHandler({CustomerNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleConfigurationException(Exception ex, WebRequest request) {
    logger.debug(ex.getMessage());
    return this.getErrorMessage(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({EmailNotValidException.class, UsernameNotValidException.class})
  public ResponseEntity<ErrorResponse> handleNotValidException(Exception ex, WebRequest request) {
    logger.debug(ex.getMessage());
    return this.getErrorMessage(ex, HttpStatus.CONFLICT);
  }


  @ExceptionHandler({IOException.class, AppConfigException.class, NamingException.class, JsonProcessingException.class,
      NullPointerException.class, Exception.class})
  public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex, WebRequest request) {
    logger.error("Exception: {}", ex.getLocalizedMessage());
    StackTraceElement[] stackTrace = ex.getStackTrace();
    if (stackTrace != null) {
      for (StackTraceElement stackTraceElement : stackTrace) {
        logger.error("at {}.{}:{}", stackTraceElement.getClassName(), stackTraceElement.getMethodName(),
            stackTraceElement.getLineNumber());
      }
    }
    ex.printStackTrace(System.out);
    return this.getErrorMessage(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> getErrorMessage(Exception ex, HttpStatus httpStatus) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ErrorResponse errors = new ErrorResponse();
    errors.setTimestamp(localDateTimeProvider.now());
    errors.setMessage(ex.getMessage() != null ? ex.getMessage() : ex.getLocalizedMessage());
    errors.setResponseCode(httpStatus.value());

    return new ResponseEntity<>(errors, headers, httpStatus);

  }
}

