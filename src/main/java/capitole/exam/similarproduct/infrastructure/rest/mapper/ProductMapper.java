package capitole.exam.similarproduct.infrastructure.rest.mapper;

import capitole.exam.similarproduct.domain.model.ProductDetail;
import capitole.exam.similarproduct.infrastructure.rest.dto.ProductDetailResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductDetailResponse toResponse(ProductDetail productDetail) {
    if (productDetail == null) {
      return null;
    }
    return new ProductDetailResponse(
        productDetail.getName(),
        productDetail.getPrice(),
        productDetail.isAvailability()
    );
  }



}
