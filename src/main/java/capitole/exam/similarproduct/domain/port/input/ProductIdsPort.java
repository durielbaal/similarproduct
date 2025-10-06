package capitole.exam.similarproduct.domain.port.input;

import capitole.exam.similarproduct.application.query.GetProductIdsQuery;
import reactor.core.publisher.Flux;

public interface ProductIdsPort {
  public Flux<String> execute(GetProductIdsQuery input);
}
