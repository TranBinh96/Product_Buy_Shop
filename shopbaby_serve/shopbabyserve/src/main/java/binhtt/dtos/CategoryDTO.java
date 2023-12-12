package binhtt.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "not empty name")
    private  String name;
    @NotEmpty(message = "not empty address")
    private  String address;
}

