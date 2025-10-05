package capitole.exam.similarproduct.application.useCase;

import capitole.exam.similarproduct.application.query.GetProductDetailQuery;
import capitole.exam.similarproduct.application.query.GetProductIdsQuery;
import capitole.exam.similarproduct.domain.model.ProductDetail;
import capitole.exam.similarproduct.infrastructure.persistance.adapter.ProductDetailRepositoryAdapter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class GetProductDetailUseCase implements UseCase<GetProductDetailQuery, Mono<ProductDetail>>{
  private final ProductDetailRepositoryAdapter productDetailRepositoryAdapter;
  public GetProductDetailUseCase(ProductDetailRepositoryAdapter productDetailRepositoryAdapter) {
    this.productDetailRepositoryAdapter = productDetailRepositoryAdapter;
  }

  @Override
  public Mono<ProductDetail> execute(GetProductDetailQuery input) {
    return productDetailRepositoryAdapter.findProductDetail(input.id());
  }
}
