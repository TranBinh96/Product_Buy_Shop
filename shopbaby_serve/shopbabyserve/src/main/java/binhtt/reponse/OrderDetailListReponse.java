package binhtt.reponse;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDetailListReponse {
    List<OrderDetailReponse> orderDetais ;
    int totalPage;

}
