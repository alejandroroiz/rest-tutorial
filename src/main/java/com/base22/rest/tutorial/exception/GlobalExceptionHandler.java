package com.cnh.portal.directoryservice.exception;

import com.cnh.portal.commonutils.exceptions.AppConfigException;
import com.cnh.portal.directoryservice.Globals;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.disthub2.impl.matching.selector.ParseException;
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
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LogManager.getLogger();

  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<ErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {
    this.cleanGlobalsMemory();
    logger.info(ex.getMessage());
    return this.getErrorMessage(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> customHandleBadRequest(Exception ex, WebRequest request) {
    this.cleanGlobalsMemory();
    logger.info(ex.getMessage());
    return this.getErrorMessage(ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponse> customHandleForbidden(Exception ex, WebRequest request) {
    this.cleanGlobalsMemory();
    logger.info(ex.getMessage());
    return this.getErrorMessage(ex, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> customHandleUnauthorized(Exception ex, WebRequest request) {
    this.cleanGlobalsMemory();
    logger.info(ex.getMessage());
    return this.getErrorMessage(ex, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler({IOException.class, ParseException.class, AppConfigException.class, NamingException.class,
      JsonProcessingException.class, NullPointerException.class, Exception.class})
  public ResponseEntity<ErrorResponse> customHandleException(Exception ex, WebRequest request) {
    this.cleanGlobalsMemory();
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
    errors.setTimestamp(LocalDateTime.now());
    errors.setMessage(ex.getMessage() != null ? ex.getMessage() : ex.getLocalizedMessage());
    errors.setResponseCode(httpStatus.value());

    return new ResponseEntity<>(errors, headers, httpStatus);

  }

  private void cleanGlobalsMemory() {
    Globals.LOGGED_USER_ID.remove();
    Globals.LOGGED_USER.remove();
  }
}

