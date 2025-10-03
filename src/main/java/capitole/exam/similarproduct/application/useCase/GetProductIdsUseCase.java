package capitole.exam.similarproduct.application.useCase;

import capitole.exam.similarproduct.application.bus.UseCaseBus;
import capitole.exam.similarproduct.application.query.GetProductIdsQuery;
import capitole.exam.similarproduct.domain.model.ProductDetail;
import capitole.exam.similarproduct.infrastructure.persistance.adapter.ProductDetailRepositoryAdapter;
import java.math.BigDecimal;
import reactor.core.publisher.Flux;

public class GetProductIdsUseCase implements UseCase<GetProductIdsQuery, Flux<BigDecimal>> {
  private final ProductDetailRepositoryAdapter productDetailRepositoryAdapter;

  public GetProductIdsUseCase(ProductDetailRepositoryAdapter productDetailRepositoryAdapter) {
    this.productDetailRepositoryAdapter = productDetailRepositoryAdapter;
  }

  @Override
  public Flux<BigDecimal> execute(GetProductIdsQuery input) {
    return productDetailRepositoryAdapter.findSimilarProductIds(input.id());
  }
}
