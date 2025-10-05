package capitole.exam.similarproduct.infrastructure.exception.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<String> handleProductNotFound(ProductNotFoundException ex) {
    return ResponseEntity.ok(ex.getMessage());
  }

  @ExceptionHandler(ProductIdNotValidException.class)
  public ResponseEntity<String> handleProductIdNotValid(ProductIdNotValidException ex) {
    return ResponseEntity.ok(ex.getMessage());
  }


}
