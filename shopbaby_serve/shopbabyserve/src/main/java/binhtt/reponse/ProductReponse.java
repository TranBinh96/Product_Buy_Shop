package binhtt.reponse;

import binhtt.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductReponse extends  BaseReponse {

    @JsonProperty("name")
    private  String name;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category_id")
    private  long categoryId ;

    public static ProductReponse fromProduct(Product product){
        ProductReponse productReponse = ProductReponse
                .builder()
                .name(product.getName())
                .thumbnail(product.getThumbnail())
                .price(product.getPrice())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
        productReponse.setCreateAt(product.getCreateAt());
        productReponse.setUpdateAt(product.getUpdateAt());
        return  productReponse;
    }
}
