package capitole.exam.similarproduct.infrastructure.config;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Value("${webclient.mock.api.base-url}")
  private String mockApiBaseUrl;

  @Value("${webclient.request.timeout}")
  private int timeout;

  @Bean
  public WebClient webClient() {
    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .responseTimeout(Duration.ofSeconds(timeout));

    ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

    return WebClient.builder()
        .baseUrl(mockApiBaseUrl)
        .clientConnector(connector)
        .build();
  }
}
