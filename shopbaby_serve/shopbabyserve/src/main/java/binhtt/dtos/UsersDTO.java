package binhtt.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

   @JsonProperty("address")
   private String address;

   @NotBlank(message = "Password can't be blank ")
   private  String password;

   @JsonProperty("retype_password")
   private  String retypePassword;

   @JsonProperty("date_of_birthday")
   private Date dateOfBirthday;

   @JsonProperty("facebook_account_id")
   private int facebookAccountId;

   @JsonProperty("google_account_id")
   private  int googleAccountId;

   @JsonProperty("role_id")
   private long roleId;

}
