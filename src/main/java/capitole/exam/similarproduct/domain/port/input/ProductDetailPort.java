package capitole.exam.similarproduct.domain.port.input;

import capitole.exam.similarproduct.application.query.GetProductDetailQuery;
import capitole.exam.similarproduct.domain.model.ProductDetail;
import reactor.core.publisher.Mono;

public interface ProductDetailPort {
  public Mono<ProductDetail> execute(GetProductDetailQuery input);

}
