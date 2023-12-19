package binhtt.reponse;

import binhtt.dtos.OrderDetailsDTO;
import binhtt.models.Order;
import binhtt.models.OrderDetail;
import binhtt.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDetailReponse extends  BaseReponse {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("price")
    private  float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private float totalMoney;

    @JsonProperty("color")
    private String color;

    public  static OrderDetailReponse fromOrderDetail(OrderDetail orderDetail){
        OrderDetailReponse orderDetailReponse =  OrderDetailReponse
                .builder()
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();
        orderDetailReponse.setCreateAt(orderDetail.getCreateAt());
        orderDetailReponse.setUpdateAt(orderDetail.getUpdateAt());
        return orderDetailReponse;
    }

}
