package capitole.exam.similarproduct.infrastructure.persistance.adapter;

import capitole.exam.similarproduct.domain.model.ProductDetail;
import java.math.BigDecimal;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class ProductDetailRepositoryAdapter {
  private final WebClient webClient;

  public ProductDetailRepositoryAdapter(WebClient webClient) {
    this.webClient = webClient;
  }

  public Flux<BigDecimal> findSimilarProductIds(String productId) {
    return webClient.get()
        .uri("/product/{productId}/similarids", productId)
        .retrieve()
        .bodyToFlux(BigDecimal.class);
  }

  public ProductDetail findProductDetail(String productId) {
    return webClient.get()
        .uri("/product/{productId}", productId)
        .retrieve()
        .bodyToMono(ProductDetail.class)
        .block();
  }


}
