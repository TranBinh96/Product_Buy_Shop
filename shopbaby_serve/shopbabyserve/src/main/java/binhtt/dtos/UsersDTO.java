package binhtt.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersDTO {
   @JsonProperty("fullname")
   private String fullName;

   @JsonProperty("phone_number")
   @NotBlank(message = "Phone number is required")
   private  String phoneNumber;

   private String address;

   @NotBlank(message = "Password can't be blank ")
   private  String password;

   @JsonProperty("retype_password")
   private  String retypePassword;

   @JsonProperty("date_of_birthday")
   private LocalDateTime dateOfBirthday;

   @JsonProperty("facebook_account_id")
   private int facebookAccountId;

   @JsonProperty("google_account_id")
   private  int googleAccountId;

   @JsonProperty("role_id")
   @NotBlank(message = "role id number is required")
   private Long roleId;

}
