package binhtt.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @NotBlank(message = "Empty name not null")
    @Size(min = 3,max = 200,message = "Title must be between 3 and 200 characters")
    @JsonProperty("name")
    private  String name;
    @Min(value= 0,message = "Price must be greater than or equal to 0")
    @Max(value =100000 ,message = "Price must the le less than or equal to 10,000,000")
    @JsonProperty("price")
    private Float price;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("description")
    private String description;

    @JsonProperty("category_id")
    private  long categoryId ;


}
