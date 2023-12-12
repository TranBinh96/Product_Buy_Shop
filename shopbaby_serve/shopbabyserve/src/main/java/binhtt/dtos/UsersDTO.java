package binhtt.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersDTO {
    @NotBlank(message = "Please inpit data")
    private  String username;
    @Size(min =3 ,max =12 ,message = "Password must be between 3 and 200 characters")
    private  String password;
}
