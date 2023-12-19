package binhtt.reponse;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderListReponse {
    private  List<OrderReponse> orders;
    private  int totalPage;




}
