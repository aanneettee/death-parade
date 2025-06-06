package com.example.deathparade.exceptions;

import org.springframework.http.HttpStatus;

/**.
 *
 */

public class NotFoundException extends ApiException {
  /**.
   *
   */
  public NotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
