package binhtt.reponse;

import binhtt.dtos.CategoryDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoryListReponse {
    private  List<CategoryDTO> categorys;
    private  int totalPage;
}
