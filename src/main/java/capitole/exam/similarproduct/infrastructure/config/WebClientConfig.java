package capitole.exam.similarproduct.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Value("${webclient.mock.api.base-url}")
  private String mockApiBaseUrl;

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl(mockApiBaseUrl)
        .build();
  }
}
