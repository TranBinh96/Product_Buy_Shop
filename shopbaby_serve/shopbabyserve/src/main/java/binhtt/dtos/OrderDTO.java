package binhtt.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1,message = "User's ID must be >0")
    private Long userId ;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 5 ,message = "Phone must to long >5")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("total_money")
    @Min(value = 1 ,message ="Total Money must be > 0" )
    private Float totalMoney;

    @JsonProperty("order_date")
    private Date order_date;

    @JsonProperty("tracking_number")
    private String trackingNumber;
    @JsonProperty("shipping_date")
    private LocalDate shippingDate;
    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private  String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;

}
