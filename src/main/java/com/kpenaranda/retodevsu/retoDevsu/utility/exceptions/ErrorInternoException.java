package com.kpenaranda.retodevsu.retoDevsu.utility.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ErrorInternoException extends RuntimeException {

  public ErrorInternoException(String message) {
    super(message);
  }
}
