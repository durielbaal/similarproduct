package capitole.exam.similarproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SimilarproductApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimilarproductApplication.class, args);
    System.out.println("Hello World");
	}

}
