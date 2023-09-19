package com.kpenaranda.retodevsu.retoDevsu.utility.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoEncontradoException extends RuntimeException {

  public NoEncontradoException(String message) {
    super(message);
  }
}
