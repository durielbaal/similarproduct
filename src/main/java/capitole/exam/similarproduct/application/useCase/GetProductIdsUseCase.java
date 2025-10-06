package capitole.exam.similarproduct.application.useCase;

import capitole.exam.similarproduct.application.query.GetProductIdsQuery;
import capitole.exam.similarproduct.domain.port.input.ProductIdsPort;
import capitole.exam.similarproduct.domain.port.output.ProductDetailRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
@Component
public class GetProductIdsUseCase implements UseCase<GetProductIdsQuery, Flux<String>>, ProductIdsPort {
  private final ProductDetailRepository productDetailRepository;

  public GetProductIdsUseCase(ProductDetailRepository productDetailRepository) {
    this.productDetailRepository = productDetailRepository;

  }

  @Override
  public Flux<String> execute(GetProductIdsQuery input) {
    return productDetailRepository.findSimilarProductIds(input.id());
  }
}
