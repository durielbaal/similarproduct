package capitole.exam.similarproduct.domain.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetail {
  private String  id;

  public String getId() {
    return id;
  }

  public boolean isAvailability() {
    return availability;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }

  private String name;
  private BigDecimal price;
  private boolean availability;
}
