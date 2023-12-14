package binhtt.reponse;

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
}
