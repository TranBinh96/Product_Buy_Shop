package binhtt.reponse;

import binhtt.dtos.OrderDTO;
import binhtt.models.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReponse extends BaseReponse{
    @JsonProperty("id")
    private Long Id;
    @JsonProperty("user_id")
    private Long userId ;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("order_date")
    private Date order_date;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private  String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;

    public  static OrderReponse fromOrder(Order order){
        OrderReponse orderReponse = OrderReponse.builder()
                 .Id(order.getId())
                .userId(order.getUser().getId())
                .fullname(order.getFullname())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .note(order.getNote())
                .order_date(order.getOrder_date())
                .totalMoney(order.getTotalMoney())
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .build();
        orderReponse.setCreateAt(order.getCreateAt());
        orderReponse.setUpdateAt(order.getUpdateAt());
        return  orderReponse;
    }

}
