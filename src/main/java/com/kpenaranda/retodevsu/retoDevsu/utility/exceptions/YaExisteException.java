package com.kpenaranda.retodevsu.retoDevsu.utility.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class YaExisteException extends RuntimeException {

  public YaExisteException(String message) {
    super(message);
  }
}