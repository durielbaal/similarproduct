package capitole.exam.similarproduct.application.useCase;

import capitole.exam.similarproduct.application.query.GetProductDetailQuery;
import capitole.exam.similarproduct.domain.model.ProductDetail;
import capitole.exam.similarproduct.domain.port.input.ProductDetailPort;
import capitole.exam.similarproduct.domain.port.output.ProductDetailRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class GetProductDetailUseCase implements UseCase<GetProductDetailQuery, Mono<ProductDetail>>, ProductDetailPort {
  private final ProductDetailRepository productDetailRepository;

  public GetProductDetailUseCase(ProductDetailRepository productDetailRepository) {
    this.productDetailRepository = productDetailRepository;
  }

  @Override
  public Mono<ProductDetail> execute(GetProductDetailQuery input) {
    return productDetailRepository.findProductDetail(input.id());
  }
}
