package capitole.exam.similarproduct.infrastructure.rest.controller;

import capitole.exam.similarproduct.application.query.GetProductDetailQuery;
import capitole.exam.similarproduct.application.query.GetProductIdsQuery;
import capitole.exam.similarproduct.application.useCase.GetProductDetailUseCase;
import capitole.exam.similarproduct.application.useCase.GetProductIdsUseCase;
import capitole.exam.similarproduct.domain.model.ProductDetail;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/product")
public class ProductDetailController {

  private final GetProductDetailUseCase getProductDetailUseCase;
  private final GetProductIdsUseCase getProductIdsUseCase;
  public ProductDetailController(GetProductDetailUseCase getProductDetailUseCase, GetProductIdsUseCase getProductIdsUseCase) {
    this.getProductDetailUseCase = getProductDetailUseCase;
    this.getProductIdsUseCase = getProductIdsUseCase;
  }


  @GetMapping("/{productId}")
  public ProductDetail getProductDetail(@PathVariable String productId) {
    GetProductDetailQuery query = new GetProductDetailQuery(productId);
    return getProductDetailUseCase.execute(query);
  }

  @GetMapping("/{productId}/similarids")
  public Flux<BigDecimal> getProductIds(@PathVariable String productId) {
    GetProductIdsQuery query = new GetProductIdsQuery(productId);
    return getProductIdsUseCase.execute(query);
  }
}