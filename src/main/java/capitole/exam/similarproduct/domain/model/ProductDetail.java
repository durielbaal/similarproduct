package capitole.exam.similarproduct.domain.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetail {
  private String  id;
  private String name;
  private BigDecimal price;
  private boolean availability;
}
