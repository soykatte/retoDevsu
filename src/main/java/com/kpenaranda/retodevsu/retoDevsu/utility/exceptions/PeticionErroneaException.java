package com.kpenaranda.retodevsu.retoDevsu.utility.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PeticionErroneaException extends RuntimeException {

  public PeticionErroneaException(String message) {
    super(message);
  }
}
