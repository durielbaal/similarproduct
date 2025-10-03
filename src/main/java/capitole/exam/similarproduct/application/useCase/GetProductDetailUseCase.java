package capitole.exam.similarproduct.application.useCase;

import capitole.exam.similarproduct.application.query.GetProductDetailQuery;
import capitole.exam.similarproduct.application.query.GetProductIdsQuery;
import capitole.exam.similarproduct.domain.model.ProductDetail;
import capitole.exam.similarproduct.infrastructure.persistance.adapter.ProductDetailRepositoryAdapter;

public class GetProductDetailUseCase implements UseCase<GetProductDetailQuery, ProductDetail>{
  private final ProductDetailRepositoryAdapter productDetailRepositoryAdapter;
  public GetProductDetailUseCase(ProductDetailRepositoryAdapter productDetailRepositoryAdapter) {
    this.productDetailRepositoryAdapter = productDetailRepositoryAdapter;
  }

  @Override
  public ProductDetail execute(GetProductDetailQuery input) {
    return productDetailRepositoryAdapter.findProductDetail(input.id());
  }
}
