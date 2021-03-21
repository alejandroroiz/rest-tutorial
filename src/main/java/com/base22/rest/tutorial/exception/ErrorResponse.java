package com.base22.rest.tutorial.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErrorResponse {

  @JsonFormat(shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd hh:mm:ss")
  private LocalDateTime timestamp;
  private int responseCode;
  private String message;

  public void setTimestamp(LocalDateTime now) {
    this.timestamp = now;

  }

  public int getResponseCode() {
    return this.responseCode;
  }

  public void setResponseCode(int value) {
    this.responseCode = value;

  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;

  }

}

