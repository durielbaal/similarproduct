package capitole.exam.similarproduct.domain.port.output;

import capitole.exam.similarproduct.domain.model.ProductDetail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductDetailRepository {
  public Flux<String> findSimilarProductIds(String productId);
  public Mono<ProductDetail> findProductDetail(String productId);
}
