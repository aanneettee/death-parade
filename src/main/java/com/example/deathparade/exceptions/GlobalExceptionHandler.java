package com.example.deathparade.exceptions;

import com.example.deathparade.models.dto.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**.
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

  private ErrorResponseDto buildErrorResponse(HttpStatus status,
                                              Exception ex, HttpServletRequest request) {
    return new ErrorResponseDto(
            LocalDateTime.now(),
            status.value(),
            ex.getMessage(),
            request.getRequestURI()
    );
  }

  /**.
   *
   */

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorResponseDto> handleApiExceptions(ApiException ex,
                                                              HttpServletRequest request) {
    ErrorResponseDto errorResponse = buildErrorResponse(ex.getStatus(), ex, request);
    return ResponseEntity.status(ex.getStatus()).body(errorResponse);
  }

  /**.
   *
   */

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleValidationErrors(
          MethodArgumentNotValidException ex,
          HttpServletRequest request) {
    String errorMessage = "Validation failed: " + ex.getBindingResult().getFieldErrors()
            .get(0).getDefaultMessage();
    ErrorResponseDto errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    errorResponse.setMessage(errorMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**.
   *
   */

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponseDto> handleArgumentTypeMismatch(
          MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
    String errorMessage = String.format("Invalid type for argument \"%s\"", ex.getName());
    ErrorResponseDto errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    errorResponse.setMessage(errorMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**.
   *
   */

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGenericException(
          Exception ex, HttpServletRequest request) {
    String errorMessage = "An error occurred";
    ErrorResponseDto errorResponse =
            buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    errorResponse.setMessage(errorMessage);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}