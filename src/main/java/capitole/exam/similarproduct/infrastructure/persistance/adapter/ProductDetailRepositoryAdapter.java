package capitole.exam.similarproduct.infrastructure.persistance.adapter;

import capitole.exam.similarproduct.domain.model.ProductDetail;
import capitole.exam.similarproduct.domain.port.output.ProductDetailRepository;
import capitole.exam.similarproduct.infrastructure.exception.product.ExternalServiceProductTimerException;
import capitole.exam.similarproduct.infrastructure.exception.product.ProductIdNotValidException;
import capitole.exam.similarproduct.infrastructure.exception.product.ProductNotFoundException;
import capitole.exam.similarproduct.infrastructure.validator.ProductValidator;
import io.netty.handler.timeout.ReadTimeoutException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Component
public class ProductDetailRepositoryAdapter implements ProductDetailRepository {
  private final WebClient webClient;
  @Value("${webclient.product.detail.url}")
  private String productDetailUrl;
  @Value("${webclient.product.similar.ids.url}")
  private String productSimilarIdsUrl;
  public ProductDetailRepositoryAdapter(WebClient webClient) {
    this.webClient = webClient;
  }
  @Override
  public Flux<String> findSimilarProductIds(String productId) {
    if (!ProductValidator.validateProductId(productId)) {
      return Flux.error(new ProductIdNotValidException());
    }

    return webClient.get()
        .uri(productSimilarIdsUrl, productId)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
          if (clientResponse.statusCode().value() == 404) {
            return Mono.error(new ProductNotFoundException(productId));
          }
          return clientResponse.createException();
        })
        .onStatus(HttpStatusCode::is5xxServerError, ClientResponse::createException)
        .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
        .onErrorMap(e -> {
          Throwable rootCause = (e.getCause() != null) ? e.getCause() : e;

          if (rootCause instanceof ReadTimeoutException ||
              rootCause instanceof TimeoutException) {

            return new ExternalServiceProductTimerException();
          }
          return e;
        })
        .flatMapMany(Flux::fromIterable);
  }
  @Override
  public Mono<ProductDetail> findProductDetail(String productId) {
    if(!ProductValidator.validateProductId(productId)){
      return Mono.error(new ProductIdNotValidException());
    }
    return webClient.get()
        .uri(productDetailUrl, productId)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
          if (clientResponse.statusCode().value() == 404) {
            return Mono.error(new ProductNotFoundException(productId));
          }
          return clientResponse.createException();
        })
        .onStatus(HttpStatusCode::is5xxServerError, ClientResponse::createException)
        .bodyToMono(ProductDetail.class)
        .onErrorMap(e -> {
          Throwable rootCause = (e.getCause() != null) ? e.getCause() : e;

          if (rootCause instanceof ReadTimeoutException ||
              rootCause instanceof TimeoutException) {

            return new ExternalServiceProductTimerException();
          }
          return e;
        });

  }
}



