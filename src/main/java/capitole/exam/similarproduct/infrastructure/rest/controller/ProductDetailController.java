package capitole.exam.similarproduct.infrastructure.rest.controller;

import capitole.exam.similarproduct.application.query.GetProductDetailQuery;
import capitole.exam.similarproduct.application.query.GetProductIdsQuery;
import capitole.exam.similarproduct.domain.model.ProductDetail;
import capitole.exam.similarproduct.domain.port.input.ProductDetailPort;
import capitole.exam.similarproduct.domain.port.input.ProductIdsPort;
import capitole.exam.similarproduct.infrastructure.rest.dto.ProductDetailResponse;
import capitole.exam.similarproduct.infrastructure.rest.mapper.ProductMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
@Validated
public class ProductDetailController {

  private final ProductDetailPort productDetailPort;
  private final ProductIdsPort productIdsPort;
  private final ProductMapper productMapper;
  public ProductDetailController(ProductDetailPort productDetailPort,
      ProductIdsPort productIdsPort,
      ProductMapper productMapper) {
    this.productDetailPort = productDetailPort;
    this.productIdsPort = productIdsPort;
    this.productMapper = productMapper;
  }


  @GetMapping("/{productId}")
  public Mono<ProductDetailResponse> getProductDetail(@PathVariable String productId) {
    GetProductDetailQuery query = new GetProductDetailQuery(productId);
    Mono<ProductDetail> productDetail = productDetailPort.execute(query);
    return productDetail.map(productMapper::toResponse);
  }

  @GetMapping("/{productId}/similarids")
  public Flux<String> getProductIds(@PathVariable  String productId) {
    GetProductIdsQuery query = new GetProductIdsQuery(productId);
    return productIdsPort.execute(query);
  }
  @GetMapping("/{productId}/similar")
  public Flux<ProductDetailResponse> getSimilarProductDetails(@PathVariable String productId) {
    return productIdsPort.execute(new GetProductIdsQuery(productId))
        .flatMap(id -> productDetailPort
            .execute(new GetProductDetailQuery(id))
            .map(productMapper::toResponse));
  }


}