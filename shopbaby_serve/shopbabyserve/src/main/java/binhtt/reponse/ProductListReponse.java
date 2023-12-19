package binhtt.reponse;

import binhtt.models.Product;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductListReponse {
    private  List<ProductReponse> products;
    private  int totalPage;




}
