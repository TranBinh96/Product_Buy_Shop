package binhtt.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    @ManyToOne
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product Image must be > 1 ")
    private long productId;

    @JsonProperty("image_url")
    @Size(min = 1,max = 200, message = "Image's name")
    private  String imageUrl;
}
