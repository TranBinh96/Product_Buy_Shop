package binhtt.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailsDTO {
    @JsonProperty("order_id")
    @NotNull(message = "Order ID number is required")
    private Long orderId ;
    @JsonProperty("product_id")
    @NotNull(message = "Product ID number is required")
    private Long productId;
    private Float price ;
    @JsonProperty("number_of_products")
    private int numberOfProducts;
    private String color;
}
