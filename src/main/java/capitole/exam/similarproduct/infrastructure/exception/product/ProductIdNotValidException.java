package capitole.exam.similarproduct.infrastructure.exception.product;

public class ProductIdNotValidException extends RuntimeException {
  public ProductIdNotValidException() {
    super(ProductErrorMessages.PRODUCT_NOT_VALID_FORMAT);
  }


}
